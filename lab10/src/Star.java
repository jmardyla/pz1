import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Star implements XmasShape {
    List<XmasShape> shapes = new ArrayList<>();
    int x;
    int y;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 5; i++) {
            Triangle t = new Triangle(-13,-45);
            shapes.add(t);
        }
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        for (var t : shapes) {
            g2d.rotate(2*Math.PI / 5);
            t.draw(g2d);
        }
    }
}
