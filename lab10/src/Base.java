import java.awt.*;

public class Base implements XmasShape {
    int x, y;
    double scale;

    public Base(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public Base(int x, int y){
        this(x, y, 1);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        int[] x = {0, 100, 100, 0};
        int[] y = {0, 0, 100, 100};

//        g2d.setColor(new Color(87, 44, 10, 255));
        GradientPaint grad = new GradientPaint(0, 0, new Color(134, 68, 13), 0, 80, new Color(91, 53, 9));
        g2d.setPaint(grad);
        g2d.fillPolygon(x, y, x.length);
    }
}
