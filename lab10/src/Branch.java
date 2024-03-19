import java.awt.*;

public class Branch implements XmasShape {
    int x;
    int y;
    double scale;

    Color color;

    public Branch(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public Branch(int x, int y) {
        this(x, y, 0);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        int xVariance = (int) (120 * scale);
        int yVariance = (int) (150 * scale);

        int[] x = {-xVariance, 100, 200 + xVariance};
        int[] y = {100 + yVariance, 0, 100 + yVariance};

//        g2d.setColor(new Color(9, 110, 22, 255));
        GradientPaint grad = new GradientPaint(0, 0, new Color(17, 168, 17), 0, 250, new Color(4, 87, 4));
        g2d.setPaint(grad);
        g2d.fillPolygon(x, y, x.length);
    }
}
