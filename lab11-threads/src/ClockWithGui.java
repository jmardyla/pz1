import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

public class ClockWithGui extends JPanel {

    LocalTime time = LocalTime.now();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        ClockWithGui clockWithGui = new ClockWithGui();
        frame.setContentPane(clockWithGui);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        clockWithGui.new ClockThread().start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        g2d.translate(centerX, centerY);

        for (int i = 1; i < 13; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 12 * i);
            Point2D src = new Point2D.Float(0, -120);
            Point2D trg = new Point2D.Float();
            at.transform(src, trg);
            g2d.drawString(Integer.toString(i), (int) trg.getX(), (int) trg.getY());
        }

        g2d.translate(centerX * 0.01, -centerY * 0.01);

        g2d.setStroke(new BasicStroke(4, CAP_ROUND, JOIN_MITER));
        AffineTransform saveAT = g2d.getTransform();
        g2d.rotate(time.getHour() % 12 * 2 * Math.PI / 12 + time.getMinute() * 2 * Math.PI / 12 / 60);
        g2d.drawLine(0, 0, 0, -75);
        g2d.setTransform(saveAT);

        g2d.rotate(time.getMinute() * 2 * Math.PI / 60 + (time.getSecond() * 2 * Math.PI / 3600));
        g2d.drawLine(0, 0, 0, -100);
        g2d.setTransform(saveAT);

        g2d.setStroke(new BasicStroke(2, CAP_ROUND, JOIN_MITER));
        g2d.rotate(time.getSecond() * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -115);
        g2d.setTransform(saveAT);

//        g2d.drawLine(0, -100, 0, -125);

        g2d.setStroke(new BasicStroke(1, CAP_ROUND, JOIN_MITER));
        for (int i = 0; i < 60; i++) {
            g2d.drawLine(10, -130, 10, -135);
            g2d.rotate(2 * Math.PI / 60);
        }
    }

    class ClockThread extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n", time.getHour(), time.getMinute(), time.getSecond());

                repaint();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}