import java.awt.*;

public class Triangle implements XmasShape {
    int x, y;

    public Triangle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        int[] x = {0, 13, 26};
        int[] y = {45, 0, 45};

        g2d.setColor(new Color(253, 242, 0));
        g2d.fillPolygon(x, y, x.length);
    }
}
