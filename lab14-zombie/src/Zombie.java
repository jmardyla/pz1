import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Zombie implements Sprite {
    BufferedImage tape;
    int x = 500;
    int y = 500;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT; // z rysunku;
    int WIDTH; // z rysunku;

    Zombie(int x, int y, double scale) throws IOException {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png")));

    }

    Zombie(int x, int y, double scale, BufferedImage tape) throws IOException {
        this(x, y, scale);
        this.tape = tape;
        HEIGHT = tape.getHeight();
        WIDTH = tape.getWidth() / 10;
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */

    public void draw(Graphics g, JPanel parent) {
        Image img = tape.getSubimage(index * WIDTH, 0, WIDTH, HEIGHT); // pobierz klatkę
        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next() {
        x -= 20 * scale;
        index = (index + 1) % 10;
    }

    @Override
    public boolean isVisble() {
        return x + (int) (WIDTH * scale) > 0;
    }

    @Override
    public boolean isHit(int _x, int _y) {
        return (_x >= x && _x <= x + (int) (WIDTH * scale) && _y >= y - (int) (HEIGHT * scale) / 2 && _y <= y + (int) (HEIGHT * scale) / 2);
    }

    @Override
    public boolean isCloser(Sprite other) {
        if (other instanceof Zombie z) {
            return scale > z.scale;
        } else {
            return Sprite.super.isCloser(other);
        }
    }

}