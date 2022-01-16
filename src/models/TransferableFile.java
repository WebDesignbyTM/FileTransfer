package models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

public class TransferableFile {
    private File currentFile;
    private Vector <TransferableFile> childrenFiles;

    public static int checkDelimiter(byte[] bytes, int offset) {
        if (offset + 4 >= bytes.length)
            return 0;

        if (bytes[offset] == bytes[offset + 4] && bytes[offset] == 0 &&
            bytes[offset + 1] == bytes[offset + 3] && bytes[offset + 1] == 1) {
            if (bytes[offset + 2] == (byte)',')
                return 1;
            else if (bytes[offset + 2] == (byte)':')
                return 2;
        }

        return 0;
    }

    public TransferableFile(String path) throws IOException {
        currentFile = new File(path);

        if (currentFile.isDirectory()) {
            Stream <Path> contents = Files.walk(currentFile.toPath(), 1);
            if (contents == null)
                return;

            childrenFiles = new Vector<>();
            List <Path> contentsList = contents.filter(path1 -> !path1.toString().equals(currentFile.getPath()) ).toList();

            for (Path p : contentsList)
                childrenFiles.addElement(new TransferableFile(p.toString()));
        }
    }

    public static void deserielize(byte[] serializedTransferableFile, int offset, String basePath) {
        int idx = offset;
        StringBuilder name = new StringBuilder();

        for (; idx < serializedTransferableFile.length &&
                TransferableFile.checkDelimiter(serializedTransferableFile, idx) == 0; ++idx) {

            name.append(Character.toString((char) serializedTransferableFile[idx]));
        }

        if (TransferableFile.checkDelimiter(serializedTransferableFile, idx) == 1) {

            File aux = new File(basePath + "\\" + name.toString());
            boolean success = aux.mkdir();
            deserielize(serializedTransferableFile, idx + 5, basePath + "\\" + name.toString());

        } else if (TransferableFile.checkDelimiter(serializedTransferableFile, idx) == 2) {
            try {

                idx += 5;

                FileOutputStream fileOutputStream = new FileOutputStream(basePath + "\\" + name.toString());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                for (; idx < serializedTransferableFile.length &&
                        TransferableFile.checkDelimiter(serializedTransferableFile, idx) == 0; ++idx) {
                    byteArrayOutputStream.write(serializedTransferableFile[idx]);
                }

                bufferedOutputStream.write(byteArrayOutputStream.toByteArray());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();

                if (idx != serializedTransferableFile.length)
                    deserielize(serializedTransferableFile, idx + 5, basePath);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
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

    public byte[] serialize() {
        Vector <Byte> serializedTF = new Vector<Byte>();
        for (Byte b : currentFile.getName().getBytes()) {
            serializedTF.add(b);
        }

        int idx = 0;
        int length = serializedTF.size() + 5;
        byte[] resultBuffer = new byte[length];

        for (; idx < serializedTF.size(); ++idx)
            resultBuffer[idx] = serializedTF.elementAt(idx);

        if (!currentFile.isDirectory()) {
            resultBuffer[idx++] = 0;
            resultBuffer[idx++] = 1;
            resultBuffer[idx++] = (byte)':';
            resultBuffer[idx++] = 1;
            resultBuffer[idx] = 0;
            try {
                FileInputStream fileInputStream = new FileInputStream(currentFile);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] aux = new byte[(int)(length + currentFile.length())];

                System.arraycopy(resultBuffer, 0, aux, 0, resultBuffer.length);
                bufferedInputStream.read(aux, resultBuffer.length, (int)currentFile.length());

                resultBuffer = aux;

            } catch (Exception exception) {
                System.out.println("hai sa fim seriosi, nu-i ca si cum mai are rost sa afisez asta" + exception.getMessage());
            }
        } else {
            resultBuffer[idx++] = 0;
            resultBuffer[idx++] = 1;
            resultBuffer[idx++] = (byte)',';
            resultBuffer[idx++] = 1;
            resultBuffer[idx] = 0;
            for (TransferableFile child : childrenFiles) {
                byte[] aux1 = child.serialize();
                byte[] aux2 = new byte[(int)resultBuffer.length + aux1.length + 5];

                System.arraycopy(resultBuffer, 0, aux2, 0, resultBuffer.length);
                aux2[resultBuffer.length] = 0;
                aux2[resultBuffer.length + 1] = 1;
                aux2[resultBuffer.length + 2] = (byte)',';
                aux2[resultBuffer.length + 3] = 1;
                aux2[resultBuffer.length + 4] = 0;
                System.arraycopy(aux1, 0, aux2, resultBuffer.length + 5, aux1.length);

                resultBuffer = aux2;
            }
        }

        return resultBuffer;
    }
}
