package controllers;

import models.FTConfiguration;

import java.io.*;
import java.util.Scanner;

public class FTConfigurationController {
    private final FTConfiguration ftConfiguration;

    public FTConfigurationController() throws Exception {
        File configFile = new File("ftConfiguration.txt");
        ftConfiguration = new FTConfiguration("", "");

        if (!configFile.exists()) {
            throw new Exception("No configuration file found!");
        }

        Scanner fileScanner = new Scanner(configFile);
        ftConfiguration.setComputerAlias(fileScanner.nextLine());
        ftConfiguration.setStartingPath(fileScanner.nextLine());
    }

    public static void editConfigurationFile(String computerAlias, String startingPath,
                                             FTConfigurationController ftConfigurationController) throws Exception {
        File configFile = new File("ftConfiguration.txt");

        if (!configFile.exists()) {
            if (!configFile.createNewFile())
                throw new Exception("There was a problem creating the configuration file!");
        }

        FileWriter fileWriter = new FileWriter(configFile);
        fileWriter.write(computerAlias);
        fileWriter.write("\r\n");
        fileWriter.write(startingPath);
        fileWriter.close();

        if (ftConfigurationController == null) {
            ftConfigurationController = new FTConfigurationController();
        } else {
            ftConfigurationController.setComputerAlias(computerAlias);
            ftConfigurationController.setStartingPath(startingPath);
        }
    }

    public String getComputerAlias() {
        return ftConfiguration.getComputerAlias();
    }

    public String getStartingPath() {
        return ftConfiguration.getStartingPath();
    }

    public void setComputerAlias(String computerAlias) {
        ftConfiguration.setComputerAlias(computerAlias);
    }

    public void setStartingPath(String startingPath) {
        ftConfiguration.setStartingPath(startingPath);
    }
}
