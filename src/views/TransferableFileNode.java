package views;

import models.TransferableFile;

import javax.swing.tree.DefaultMutableTreeNode;

public class TransferableFileNode extends DefaultMutableTreeNode {
    public TransferableFileNode(TransferableFile tf) {
        super(tf.getCurrentFile().getName(), tf.getCurrentFile().isDirectory());

        if (!tf.getCurrentFile().isDirectory())
            return;

        for (TransferableFile aux : tf.getChildrenFiles())
            this.add(new TransferableFileNode(aux));
    }
}
