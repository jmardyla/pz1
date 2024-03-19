import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here

        JFrame frame = new JFrame("Zombie");
        SpriteFactory factory = ZombieFactory.INSTANCE;
        DrawPanel panel = new DrawPanel(Main.class.getResource("/resources/6473205443_7df3397e72_b.jpg"), factory);
        panel.setForeground(Color.white);
        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.close();
            }
        });

    }
}