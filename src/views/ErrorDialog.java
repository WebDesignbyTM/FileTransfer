package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane messagePane;

    public ErrorDialog(String message) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        messagePane.setText(message);

        buttonOK.addActionListener(e -> onOK());
    }

    public void setMessage(String message) {
        messagePane.setText(message);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
