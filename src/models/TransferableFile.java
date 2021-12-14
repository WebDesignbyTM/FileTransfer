package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

public class TransferableFile {
    private final File currentFile;
    private Vector <TransferableFile> childrenFiles;

    public TransferableFile(String path) throws IOException {
        currentFile = new File(path);

        if (currentFile.isDirectory()) {
            Stream <Path> contents = Files.walk(currentFile.toPath(), 1);
            if (contents == null)
                return;

            childrenFiles = new Vector<>();
            List <Path> contentsList = contents.filter(path1 -> !path1.toString().equals(path) ).toList();

            for (Path p : contentsList)
                childrenFiles.addElement(new TransferableFile(p.toString()));
        }
    }

    public void printHierarchy(int indentation) {

        System.out.println(" ".repeat(Math.max(0, indentation)) + currentFile.toString());

        if (childrenFiles == null)
            return;

        for (TransferableFile t : childrenFiles)
            t.printHierarchy(indentation + 1);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public Vector<TransferableFile> getChildrenFiles() {
        return childrenFiles;
    }
}
