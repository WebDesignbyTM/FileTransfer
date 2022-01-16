package views;

import controllers.FTConfigurationController;
import controllers.ServerController;
import models.TransferableFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

public class BaseWrapper extends JFrame {

    private JPanel defaultView;
    private JButton changeViewButton;
    private JPanel cardView;
    private JButton configButton;
    private String previousPanel;

    private String host = "127.0.0.1";

    private FTConfigurationController ftConfigurationController;

    public BaseWrapper() {
        super("FileTransfer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        previousPanel = "connectionPanel";

        CardLayout cardLayout = (CardLayout) (cardView.getLayout());

        defaultView.setSize(600, 800);

        try {
            ftConfigurationController = new FTConfigurationController();
        } catch (Exception exception) {
            try {
                Image icon = ImageIO.read(new File("resources/remove.png"));
                icon = icon.getScaledInstance(30, 30, 0);
                JOptionPane.showMessageDialog(defaultView, exception.getMessage(), "Configuration error",
                        JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
            } catch (Exception exception1) {
                JOptionPane.showMessageDialog(defaultView, exception.getMessage(), "Configuration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        try {
            new Thread(() -> {
                ServerSocket server = null;

                try {
                    server = new ServerSocket(ServerController.PORT_NUMBER);
                    while (true) {
                        new ServerController(server.accept());
                    }
                } catch (Exception exception) {
                    System.out.println("kinda bad ngl.");
                } finally {
                    try {
                        if (server != null) {
                            try {
                                Image icon = ImageIO.read(new File("resources/check.png"));
                                icon = icon.getScaledInstance(30, 30, 0);
                                JOptionPane.showMessageDialog(defaultView, "A new file has been received!",
                                        "Transfer succeeded", JOptionPane.PLAIN_MESSAGE, new ImageIcon(icon));
                            } catch (Exception exception) {
                                JOptionPane.showMessageDialog(defaultView, "A new file has been received!",
                                        "Transfer succeeded", JOptionPane.PLAIN_MESSAGE);
                            }
                            server.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception exception) {
            try {
                Image icon = ImageIO.read(new File("resources/remove.png"));
                icon = icon.getScaledInstance(30, 30, 0);
                JOptionPane.showMessageDialog(defaultView, "An error occurred while creating the server thread." +
                        " The application may be broken", "Server error", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
            } catch (Exception exception1) {
                JOptionPane.showMessageDialog(defaultView, "An error occurred while creating the server thread." +
                        " The application may be broken", "Server error", JOptionPane.ERROR_MESSAGE);
            }
        }


        // Load views
        JPanel aux;

        aux = new ConnectionPanel(host, ServerController.PORT_NUMBER);
        aux.setName("connectionPanel");
        cardView.add(aux.getName(), aux);

        aux = new ConfigFileForm(ftConfigurationController);
        aux.setName("configFileForm");
        cardView.add(aux.getName(), aux);

        if (ftConfigurationController == null) {
            changeViewButton.setText("Establish connection");
            cardLayout.show(cardView, "configFileForm");
        } else {

            try {
                aux = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()),
                        host, ServerController.PORT_NUMBER);
                aux.setName("transferPanel");
                cardView.add(aux.getName(), aux);
            } catch (Exception exception) {
                try {
                    Image icon = ImageIO.read(new File("resources/remove.png"));
                    icon = icon.getScaledInstance(30, 30, 0);
                    JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                            "Transfer panel error", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
                } catch (Exception exception1) {
                    JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                            "Transfer panel error", JOptionPane.ERROR_MESSAGE);
                }
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
                    JPanel aux1 = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()),
                            host, ServerController.PORT_NUMBER);
                    aux1.setName("transferPanel");
                    if (cardView.getComponents().length > 2) {
                        cardView.remove(cardView.getComponent(2));
                    }

                    cardView.add(aux1.getName(), aux1);
                } catch (Exception exception) {
                    try {
                        Image icon = ImageIO.read(new File("resources/remove.png"));
                        icon = icon.getScaledInstance(30, 30, 0);
                        JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                                "Transfer panel error", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
                    } catch (Exception exception1) {
                        JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                                "Transfer panel error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                cardLayout.show(cardView, "transferPanel");
                changeViewButton.setText("Establish connection");
            } else {
                cardLayout.show(cardView, "connectionPanel");
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
                                JPanel aux12 = new TransferPanel(new TransferableFile(ftConfigurationController.getStartingPath()),
                                        host, ServerController.PORT_NUMBER);
                                aux12.setName("transferPanel");
                                if (cardView.getComponents().length > 2) {
                                    cardView.remove(cardView.getComponent(2));
                                }

                                cardView.add(aux12.getName(), aux12);
                            } catch (Exception exception) {
                                try {
                                    Image icon = ImageIO.read(new File("resources/remove.png"));
                                    icon = icon.getScaledInstance(30, 30, 0);
                                    JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
                                } catch (Exception exception1) {
                                    JOptionPane.showMessageDialog(defaultView, exception.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                        cardLayout.show(cardView, previousPanel);
                        if (Objects.equals(previousPanel, "connectionPanel")) {
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
            try {
                Image icon = ImageIO.read(new File("resources/remove.png"));
                icon = icon.getScaledInstance(30, 30, 0);
                JOptionPane.showMessageDialog(defaultView, "An error occurred while loading an icon",
                        "Resources error", JOptionPane.ERROR_MESSAGE, new ImageIcon(icon));
            } catch (Exception exception1) {
                JOptionPane.showMessageDialog(defaultView, "An error occurred while loading an icon",
                        "Resources error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
