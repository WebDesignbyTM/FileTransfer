
package controllers;

import models.TransferableFile;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientController {

    public ClientController(String host, int port, TransferableFile transferableFile) {
        try {

            System.out.println("Connecting to host " + host + " on port " + port + ".");

            Socket fileTransferSocket = null;
            OutputStream out = null;
            InputStream in = null;

            try {
                fileTransferSocket = new Socket(host, 8081);
                in = fileTransferSocket.getInputStream();
                out = fileTransferSocket.getOutputStream();
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + host);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }


            byte[] serializedBytes = transferableFile.serialize();

            out.write(serializedBytes);

            /** Closing all the resources */
            out.close();
            in.close();
            fileTransferSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}