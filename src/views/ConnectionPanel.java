package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConnectionPanel extends JPanel {
    private JPanel panel1;
    private JButton connectButton;
    private JTextField hostField;
    private JTextField portField;

    public ConnectionPanel(String host, int port) {
        super();
        hostField.setText(host);
        portField.setText(Integer.toString(port));
        add(panel1);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image icon = ImageIO.read(new File("resources/check.png"));
                    icon = icon.getScaledInstance(30, 30, 0);
                    JOptionPane.showMessageDialog(panel1, "While this component is currently useless, let's say the connection was successful!",
                            "Connection succeeded", JOptionPane.PLAIN_MESSAGE, new ImageIcon(icon));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(panel1, "While this component is currently useless, let's say the connection was successful!",
                            "Connection succeeded", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }
}
