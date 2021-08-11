package ru.netology.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private List<ClientHandlingThread> clientThreads = new CopyOnWriteArrayList<>();

    public Server() {
        Logger.INSTANCE.log("Start server!");

        try (ServerSocket serverSocket = new ServerSocket(Settings.PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {

                    ClientHandlingThread client = new ClientHandlingThread(socket);
                    clientThreads.add(client);
                    client.start();

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
