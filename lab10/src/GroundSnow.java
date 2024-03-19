import java.awt.*;

public class GroundSnow implements XmasShape {
    int x, y;
    double scale;

    public GroundSnow(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillOval(0, 0, 500, 100);
    }
}
