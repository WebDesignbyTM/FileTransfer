package views;

import controllers.FTConfigurationController;
import models.TransferableFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;

public class BaseWrapper extends JFrame {

    private JPanel defaultView;
    private JButton changeViewButton;
    private JPanel cardView;
    private JButton configButton;
    private final ErrorDialog errorDialog;
    private String previousPanel;

    private FTConfigurationController ftConfigurationController;

    public BaseWrapper() {
        super("FileTransfer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        previousPanel = "panel2";

        errorDialog = new ErrorDialog("");
        errorDialog.setSize(300, 200);
        CardLayout cardLayout = (CardLayout) (cardView.getLayout());

        try {
            ftConfigurationController = new FTConfigurationController();
        } catch (Exception exception) {
            errorDialog.setMessage(exception.getMessage());
            errorDialog.setVisible(true);
        }

        // Load views
        JPanel aux;

        aux = new SecondPanel();
        aux.setName("panel2");
        cardView.add(aux.getName(), aux);

        aux = new ConfigFileForm(ftConfigurationController);
        aux.setName("configFileForm");
        cardView.add(aux.getName(), aux);

        if (ftConfigurationController == null) {
            changeViewButton.setText("Establish connection");
            cardLayout.show(cardView, "configFileForm");
        } else {

            try {
                aux = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()));
                aux.setName("transferPanel");
                cardView.add(aux.getName(), aux);
            } catch (Exception exception) {
                errorDialog.setMessage(exception.getMessage());
                errorDialog.setVisible(true);
            }

        }


        // Display content
        setContentPane(defaultView);
        pack();
        setVisible(true);


        // Event listeners
        changeViewButton.addActionListener(e -> {
            if (Objects.equals(changeViewButton.getText(), "Transfer files")) {
                try {
                    ftConfigurationController = new FTConfigurationController();
                    JPanel aux1 = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()));
                    aux1.setName("transferPanel");
                    if (cardView.getComponents().length > 2) {
                        cardView.remove(cardView.getComponent(2));
                    }

                    cardView.add(aux1.getName(), aux1);
                } catch (Exception exception) {
                    errorDialog.setMessage(exception.getMessage());
                    errorDialog.setVisible(true);
                }
                cardLayout.show(cardView, "transferPanel");
                changeViewButton.setText("Establish connection");
            } else {
                cardLayout.show(cardView, "panel2");
                changeViewButton.setText("Transfer files");
            }
        });

        configButton.addActionListener(e -> {
            for (Component comp : cardView.getComponents()) {
                if (comp.isVisible()) {
                    if (!Objects.equals(comp.getName(), "configFileForm")) {
                        previousPanel = comp.getName();
                        cardLayout.show(cardView, "configFileForm");
                    } else {
                        if (Objects.equals(previousPanel, "transferPanel")) {
                            try {
                                ftConfigurationController = new FTConfigurationController();
                                JPanel aux12 = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()));
                                aux12.setName("transferPanel");
                                if (cardView.getComponents().length > 2) {
                                    cardView.remove(cardView.getComponent(2));
                                }

                                cardView.add(aux12.getName(), aux12);
                            } catch (Exception exception) {
                                errorDialog.setMessage(exception.getMessage());
                                errorDialog.setVisible(true);
                            }
                        }
                        cardLayout.show(cardView, previousPanel);
                        if (Objects.equals(previousPanel, "panel2")) {
                            changeViewButton.setText("Transfer files");
                        } else {
                            changeViewButton.setText("Establish connection");

                        }
                    }
                    break;
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            Image icon = ImageIO.read(new File("resources/settings-icon-14960.png"));
            icon = icon.getScaledInstance(15, 15, 0);
            configButton = new JButton(new ImageIcon(icon));
        } catch (Exception exception) {
            errorDialog.setMessage("An error occurred retrieving the contents of the resources folder");
            errorDialog.setVisible(true);
        }
    }
}
