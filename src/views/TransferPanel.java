package views;

import controllers.ClientController;
import models.TransferableFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TransferPanel extends JPanel {
    private JPanel panel1;
    private JTree tree1;
    private JTextField pleaseSelectAFileTextField;
    private JPanel panel2;
    private JScrollPane scrollPane;
    private JButton sendFileButton;
    private TransferableFileNode tft;
    private TransferableFile tf;

    private String host;
    private int port;

    public TransferPanel(TransferableFile tf, String host, int port) {
        super();
        this.tf = tf;
        this.host = host;
        this.port = port;

        add(panel1);

        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] selectedPaths = tree1.getSelectionPaths();
                TransferableFileNode transferableFileNode = null;
                try {
                    for (TreePath path : selectedPaths)
                        transferableFileNode = (TransferableFileNode) path.getLastPathComponent();
                    new ClientController(host, port, transferableFileNode.getDisplayedFile());
                    try {
                        Image icon = ImageIO.read(new File("resources/check.png"));
                        icon = icon.getScaledInstance(30, 30, 0);
                        JOptionPane.showMessageDialog(panel1, "The file has been sent!",
                                "Transfer succeeded", JOptionPane.PLAIN_MESSAGE, new ImageIcon(icon));
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(panel1, "The file has been sent!",
                                "Transfer succeeded", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (NullPointerException nullPointerException) {
                    try {
                        Image icon = ImageIO.read(new File("resources/remove.png"));
                        icon = icon.getScaledInstance(30, 30, 0);
                        JOptionPane.showMessageDialog(panel1, "Please select a file to send",
                                "No file selected", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(panel1, "Please select a file to send",
                                "No file selected", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        tft = new TransferableFileNode(tf);
        tree1 = new JTree(tft);
    }
}
