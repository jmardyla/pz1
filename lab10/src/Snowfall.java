import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snowfall implements XmasShape {
    List<XmasShape> shapes = new ArrayList<>();
    int x;
    int y;

    Snowfall(int x, int y) {
        this.x = x;
        this.y = y;
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            Snowflake b = new Snowflake( r.nextInt(1000), r.nextInt(600), 0.1);
            shapes.add(b);
        }
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        for (var b : shapes) b.draw(g2d);
    }
}
