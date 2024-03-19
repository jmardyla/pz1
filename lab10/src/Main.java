import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // write your code here
        JFrame frame = new JFrame("Choinka");
        frame.setContentPane(new DrawPanel());
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
