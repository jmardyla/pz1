import java.awt.*;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

public class Snowflake implements XmasShape {
    int x, y;
    double scale;

    Snowflake(int x, int y, double scale) {
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
        g2d.setStroke(new BasicStroke(20, CAP_ROUND, JOIN_MITER));
        for (int i = 0; i < 12; i++) {
            g2d.setColor(new Color(0xFFFFFF));
            g2d.drawLine(0, 0, 100, 100);
            g2d.rotate(2 * Math.PI / 12);
        }
    }
}
