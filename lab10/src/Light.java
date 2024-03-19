import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Light implements XmasShape {
    List<XmasShape> lights = new ArrayList<>();
    int x, y;
    int xCutoff;
    double scale;

    boolean rotate;

    public Light(int x, int y, int xCutoff, double scale, boolean rotate) {
        this.rotate = rotate;
        this.xCutoff = xCutoff;
        this.x = x;
        this.y = y;
        this.scale = scale;

        for (int j = 0; j < 10; j++) {
            Bubble b = new Bubble();
            int positionVariable = j * 50;
            if (positionVariable > xCutoff - x) break;
            b.x = positionVariable;
//                b.y = y*10+j*100;
            b.y = 100 + positionVariable;
            b.scale = 0.2;
            b.fillColor = Color.yellow;
            b.lineColor = Color.yellow;

            lights.add(b);
        }

    }

    public Light(int x, int y, int xCutoff, double scale) {
        this(x, y, xCutoff, scale, false);
    }

    public Light(int x, int y, int xCutoff) {
        this(x, y, xCutoff, 1, false);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale * 2, scale);

        if (rotate) {
            g2d.rotate(Math.PI / 2);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (var b :
                lights) {
            b.draw(g2d);
        }
    }
}
