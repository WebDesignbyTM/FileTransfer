package controllers;

import models.TransferableFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Vector;

public class ServerController extends Thread {
    public static final int PORT_NUMBER = 8081;

    protected Socket socket;
    Component container;

    public ServerController(Socket socket, Component container) {
        this.socket = socket;
        this.container = container;
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
        start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        FTConfigurationController ftConfigurationController = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            ftConfigurationController = new FTConfigurationController();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            byte[] buffer = new byte[4096];

            Vector <Byte> byteVector = new Vector<Byte>();
            int size;
            while((size = bufferedInputStream.read(buffer)) != -1) {
                for (int i = 0; i < size; ++i)
                    byteVector.add(buffer[i]);
            }

            byte[] recievedBytes = new byte[byteVector.size()];

            for (int i = 0; i < byteVector.size(); ++i)
                recievedBytes[i] = byteVector.elementAt(i);

            TransferableFile.deserielize(recievedBytes, 0, ftConfigurationController.getDownloadPath());

            try {
                Image icon = ImageIO.read(new File("resources/check.png"));
                icon = icon.getScaledInstance(30, 30, 0);
                JOptionPane.showMessageDialog(container, "A new file has been received!",
                        "Transfer succeeded", JOptionPane.PLAIN_MESSAGE, new ImageIcon(icon));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(container, "A new file has been received!",
                        "Transfer succeeded", JOptionPane.PLAIN_MESSAGE);
            }

            System.out.println("Connection to " + socket.getInetAddress().getHostAddress() + " has been terminated.");


        } catch (IOException ex) {
            System.out.println("Unable to get streams from client");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

}