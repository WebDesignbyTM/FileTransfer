package controllers;

import models.FTConfiguration;

import java.io.*;
import java.util.Scanner;

public class FTConfigurationController {
    private final FTConfiguration ftConfiguration;

    public FTConfigurationController() throws Exception {
        File configFile = new File("ftConfiguration.txt");
        ftConfiguration = new FTConfiguration("", "", "");

        if (!configFile.exists()) {
            throw new Exception("No configuration file found!");
        }

        Scanner fileScanner = new Scanner(configFile);
        String buffer = fileScanner.nextLine();
        ftConfiguration.setComputerAlias(buffer);
        buffer = fileScanner.nextLine();
        File testFile = new File(buffer);

        if (!testFile.exists() || !testFile.isDirectory())
            throw new IOException("The specified base path cannot be resolved to a folder");

        String buffer1 = fileScanner.nextLine();
        testFile = new File(buffer1);

        if (!testFile.exists() || !testFile.isDirectory())
            throw new IOException("The specified download path cannot be resolved to a folder");

        ftConfiguration.setStartingPath(buffer);
        ftConfiguration.setDownloadPath(buffer1);
    }

    public static void editConfigurationFile(String computerAlias, String startingPath, String downloadPath,
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
        fileWriter.write("\r\n");
        fileWriter.write(downloadPath);
        fileWriter.close();

        if (ftConfigurationController == null) {
            ftConfigurationController = new FTConfigurationController();
        } else {
            ftConfigurationController.setComputerAlias(computerAlias);
            ftConfigurationController.setStartingPath(startingPath);
            ftConfigurationController.setDownloadPath(downloadPath);
        }
    }

    public String getComputerAlias() {
        return ftConfiguration.getComputerAlias();
    }

    public String getStartingPath() {
        return ftConfiguration.getStartingPath();
    }

    public String getDownloadPath() {
        return ftConfiguration.getDownloadPath();
    }

    public void setComputerAlias(String computerAlias) {
        ftConfiguration.setComputerAlias(computerAlias);
    }

    public void setStartingPath(String startingPath) throws Exception {
        File testFile = new File(startingPath);

        if (!testFile.exists() || !testFile.isDirectory())
            throw new IOException("The specified base path cannot be resolved to a folder");

        ftConfiguration.setStartingPath(startingPath);
    }

    public void setDownloadPath(String downloadPath) throws Exception {
        File testFile = new File(downloadPath);

        if (!testFile.exists() || !testFile.isDirectory())
            throw new IOException("The specified download path cannot be resolved to a folder");

        ftConfiguration.setDownloadPath(downloadPath);
    }
}
