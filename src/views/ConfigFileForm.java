package views;

import controllers.FTConfigurationController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigFileForm extends JPanel {
    private JTextField computerAliasField;
    private JTextField filePathField;
    private JButton saveButton;
    private JPanel containerPane;

    private final ErrorDialog errorDialog;

    public ConfigFileForm(FTConfigurationController ftConfigurationController) {
        super();
        add(containerPane);

        errorDialog = new ErrorDialog("");

        if (ftConfigurationController != null) {
            computerAliasField.setText(ftConfigurationController.getComputerAlias());
            filePathField.setText(ftConfigurationController.getStartingPath());
        }

        saveButton.addActionListener(e -> {
            try {
                FTConfigurationController.editConfigurationFile(
                        computerAliasField.getText(),
                        filePathField.getText(),
                        ftConfigurationController
                );
                System.out.println("Success");
            } catch (Exception exception) {
                errorDialog.setMessage("In ConfigFileForm:38: " + exception.getMessage());
                errorDialog.setVisible(true);
            }
        });
    }
}
