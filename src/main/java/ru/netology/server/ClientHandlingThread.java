package ru.netology.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandlingThread extends Thread {
    private Socket socket;

    public ClientHandlingThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Logger.INSTANCE.log("Client connected: " + socket);

            String line;

            while ((line = in.readLine()) != null) {
                if (line.equals("/exit")) {
                    break;
                }

                sendMessage(line);
            }

        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }

    public void sendMessage(String line) {

    }
}
