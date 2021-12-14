import models.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileTransfer {
    /*
    TODO: 1. File model - get and operate on the accessible files
    TODO: 2. General view - to be drawn
    TODO: 3. Command controller - handle input and manage file model / general view
     */
    public static void main(String[] args) throws IOException {
        TransferableFile tf = new TransferableFile(args[0]);

        tf.printHierarchy(0);
    }
}
