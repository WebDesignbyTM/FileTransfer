package views;

import models.TransferableFile;

import javax.swing.*;

public class TransferPanel extends JPanel {
    private JPanel panel1;
    private JTree tree1;
    private JTextField pleaseSelectAFileTextField;
    private JPanel panel2;
    private JScrollPane scrollPane;
    private TransferableFileNode tft;
    private TransferableFile tf;

    public TransferPanel(TransferableFile tf) {
        super();
        this.tf = tf;
        add(panel1);
    }

    private void createUIComponents() {
        tft = new TransferableFileNode(tf);
        tree1 = new JTree(tft);
    }
}
