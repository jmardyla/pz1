import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    public DrawPanel() {
        shapes.add(new Snowfall(0, 0));

        shapes.add(new GroundSnow(250, 600, 1));

        shapes.add(new Base(450, 555));

        shapes.add(new Branch(400, 290, 1.1));
        shapes.add(new Branch(400, 200, 0.8));
        shapes.add(new Branch(400, 140, 0.4));
        shapes.add(new Branch(400, 100));

        shapes.add(new Star(500, 107));

        shapes.add(new Light(445, 120, 600, 0.35));
        shapes.add(new Light(380, 300, 750, 0.35));
        shapes.add(new Light(650, 230, 920, 0.35, true));

        Color orange = new Color(0xFA8A36);
        Color lime = new Color(0x67FA36);
        double bubbleScale = 0.30;
        shapes.add(new Bubble(450, 200, bubbleScale, lime, lime));
        shapes.add(new Bubble(550, 258, bubbleScale, Color.red, Color.red));
        shapes.add(new Bubble(430, 305, bubbleScale, Color.red, Color.red));
        Color blue = new Color(0x0BB0AA);
        shapes.add(new Bubble(570, 340, bubbleScale, blue, blue));
        shapes.add(new Bubble(400, 380, bubbleScale, orange, orange));
        shapes.add(new Bubble(490, 420, bubbleScale, Color.red, Color.red));
        shapes.add(new Bubble(580, 480, bubbleScale, Color.pink, Color.pink));
        shapes.add(new Bubble(360, 485, bubbleScale, blue, blue));

//        shapes.add(new Snowflake(100, 100, 0.1));
        setBackground(new Color(6, 28, 103));
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        for(XmasShape s : shapes){
            s.draw((Graphics2D)g);
        }
    }

    public void addShape(XmasShape shape) {
        shapes.add(shape);
    }
}