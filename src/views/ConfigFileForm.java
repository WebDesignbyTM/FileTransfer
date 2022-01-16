package views;

import controllers.FTConfigurationController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ConfigFileForm extends JPanel {
    private JTextField computerAliasField;
    private JTextField filePathField;
    private JButton saveButton;
    private JPanel containerPane;
    private JTextField downloadPathField;

    public ConfigFileForm(FTConfigurationController ftConfigurationController) {
        super();
        add(containerPane);

        if (ftConfigurationController != null) {
            computerAliasField.setText(ftConfigurationController.getComputerAlias());
            filePathField.setText(ftConfigurationController.getStartingPath());
            downloadPathField.setText(ftConfigurationController.getDownloadPath());
        }

        saveButton.addActionListener(e -> {
            try {
                FTConfigurationController.editConfigurationFile(
                        computerAliasField.getText(),
                        filePathField.getText(),
                        downloadPathField.getText(),
                        ftConfigurationController
                );
                try {
                    Image icon = ImageIO.read(new File("resources/check.png"));
                    icon = icon.getScaledInstance(30, 30, 0);
                    JOptionPane.showMessageDialog(containerPane, "The configuration file has been written!",
                            "Configuration succeeded", JOptionPane.PLAIN_MESSAGE, new ImageIcon(icon));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(containerPane, "The configuration file has been written!",
                            "Configuration succeeded", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception exception) {
                try {
                    Image icon = ImageIO.read(new File("resources/remove.png"));
                    icon = icon.getScaledInstance(30, 30, 0);
                    JOptionPane.showMessageDialog(containerPane, exception.getMessage(),
                            "Configuration failed", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
                } catch (Exception exception1) {
                    JOptionPane.showMessageDialog(containerPane, exception.getMessage(),
                            "Configuration failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
