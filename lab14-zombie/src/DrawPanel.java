import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class DrawPanel extends JPanel implements CrossHairListener {
    BufferedImage background;
    List<Sprite> sprites = new ArrayList<>();
    SpriteFactory factory;
    CrossHair crossHair;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean hitRecently = new AtomicBoolean(false);
    private final AtomicInteger zombiesEscaped = new AtomicInteger(0);
    private final AtomicInteger zombiesKilled = new AtomicInteger(0);


    Timer timer = new Timer("Timer DrawPanel");

    DrawPanel(URL backgroundImagageURL, SpriteFactory factory) {
        try {
            background = ImageIO.read(backgroundImagageURL);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.factory = factory;
        this.crossHair = new CrossHair(this);
        crossHair.addCrossHairListener(this);
        AnimationThread thread = new AnimationThread();
        thread.start();
        running.set(true);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("/resources/transparent.png").getImage(),
                new Point(0, 0),
                "customCursor"
        ));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        Iterator<Sprite> iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = null;
            try {
                sprite = iterator.next();
            } catch (ConcurrentModificationException e) {
                continue;
            }
            sprite.draw(g, this);
        }

        g2d.setFont(new Font("ZombieCounter", Font.PLAIN, 20));
        String killedInfo = "Killed: " + zombiesKilled;
        int widthKilledInfo = g2d.getFontMetrics().stringWidth(killedInfo);
        g2d.drawString(killedInfo, getWidth() - widthKilledInfo - 20, 40);
        String escapedInfo = "Escaped: " + zombiesEscaped;
        int widthEscapedInfo = g2d.getFontMetrics().stringWidth(escapedInfo);
        g2d.drawString(escapedInfo, getWidth() - widthEscapedInfo - widthKilledInfo - 30, 40);

        crossHair.draw(g2d);
        addMouseListener(crossHair);
        addMouseMotionListener(crossHair);
    }

    public void close() {
        running.set(false);
        crossHair.timer.cancel();
        timer.cancel();
        System.out.println("CLOSING GAME");
    }

    @Override
    public void onShotsFired(int x, int y) {
        ListIterator<Sprite> iterator = sprites.listIterator(sprites.size());
        while (iterator.hasPrevious()) {
            Sprite sprite = iterator.previous();
            if (!hitRecently.get() && sprite.isHit(x, y)) {
                hitRecently.set(true);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        hitRecently.set(false);
                    }
                },100);
                iterator.remove();
                zombiesKilled.getAndIncrement();
                break;
            }
        }
    }

    class AnimationThread extends Thread {
        public void run() {
            for (int i = 1; running.get(); i++) {
                if (i % 20 == 0) {
                    sprites.add(factory.newSprite(getHeight()+400, (int) ((0.45 * getWidth()))));
                    sprites.sort((o1, o2) -> {
                        if (o1.isCloser(o2)) return 1;
                        else if (o2.isCloser(o1)) return -1;
                        else return 0;
                    });
                }
                repaint();
                Iterator<Sprite> iterator = sprites.iterator();
                while (iterator.hasNext()) {
                    Sprite sprite = iterator.next();
                    sprite.next();
                    if (!sprite.isVisble()) {
                        iterator.remove();
                        zombiesEscaped.getAndIncrement();
                    }
                }
                try {
                    sleep(1000 / 25);  // 25 klatek na sekundę
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}