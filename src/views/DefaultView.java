package views;

import models.TransferableFile;

import javax.swing.*;
import java.awt.*;

public class DefaultView extends JFrame {
    private JPanel panel1;
    private JTree tree1;
    private JTextField pleaseSelectAFileTextField;
    private JPanel panel2;
    private JScrollPane scrollPane;
    private TransferableFileNode tft;
    private TransferableFile tf;

    public DefaultView(TransferableFile tf) {
        this.tf = tf;
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
