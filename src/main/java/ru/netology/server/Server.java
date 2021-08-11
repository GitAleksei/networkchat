package ru.netology.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public Server() {
        Logger.INSTANCE.log("Start server!");

        try (ServerSocket serverSocket = new ServerSocket(Settings.PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    new ClientHandlingThread(socket);
                } catch (IOException ex) {
                    Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }

        Logger.INSTANCE.log("Stop server!");
    }
}
