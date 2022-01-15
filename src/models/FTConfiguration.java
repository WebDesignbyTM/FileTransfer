package models;

import java.util.Vector;

public class FTConfiguration {
    private String computerAlias;
    private String startingPath;
    private final Vector <String> transferHistory;

    public FTConfiguration(String computerAlias, String startingPath) {
        this.computerAlias = computerAlias;
        this.startingPath = startingPath;
        transferHistory = new Vector<String>();
    }

    public String getComputerAlias() {
        return computerAlias;
    }

    public void setComputerAlias(String computerAlias) {
        this.computerAlias = computerAlias;
    }

    public String getStartingPath() {
        return startingPath;
    }

    public void setStartingPath(String startingPath) {
        this.startingPath = startingPath;
    }

    public Vector<String> getTransferHistory() {
        return transferHistory;
    }
}
