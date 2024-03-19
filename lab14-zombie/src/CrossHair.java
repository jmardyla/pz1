import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;
    Timer timer = new Timer("Timer");

    List<CrossHairListener> listeners = new ArrayList<>();

    CrossHair(DrawPanel parent) {
        this.parent = parent;
    }

    /* x, y to współrzedne celownika
       activated - flaga jest ustawiana po oddaniu strzału (naciśnięciu przyciku myszy)
    */
    int x;
    int y;
    boolean activated = false;

    void draw(Graphics g) {
        if (activated) g.setColor(Color.RED);
        else g.setColor(Color.WHITE);
        g.drawLine(x-10, y, x+10, y);
        g.drawLine(x, y+10, x, y-10);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        registerMousePosition(e);
        activate(e);
        parent.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        registerMousePosition(e);
        activate(e);
        parent.repaint();
    }

    private void activate(MouseEvent e) {
        this.activated = true;
        notifyListeners();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated=false;
                parent.repaint();
            }
        },300);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        registerMousePosition(e);
        parent.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        registerMousePosition(e);
        parent.repaint();
    }

    private void registerMousePosition(MouseEvent e) {
        Point point = e.getPoint();
        x = point.x;
        y = point.y;
    }

    void addCrossHairListener(CrossHairListener e){
        listeners.add(e);
    }

    void notifyListeners(){
        for (var e : listeners) {
            e.onShotsFired(x, y);
        }
    }
}