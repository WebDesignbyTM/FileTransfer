package views;

import models.TransferableFile;

import javax.swing.*;
import java.awt.*;

public class DefaultView extends JFrame {
    private JPanel panel1;
    private JSpinner spinner1;
    private JSlider slider1;
    private JTree tree1;
    private TransferableFileNode tft;
    private TransferableFile tf;

    public DefaultView(TransferableFile tf) {
        this.tf = tf;
        panel1.setLayout(new GridLayout(2, 2));
        setContentPane(panel1);
        pack();
        setTitle("bruh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        tft = new TransferableFileNode(tf);
        tree1 = new JTree(tft);
    }
}
