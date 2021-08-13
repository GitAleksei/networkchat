package ru.netology;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private final Map<Integer, Client> clients = new ConcurrentHashMap<>();

    public Server() {
        Logger.INSTANCE.log("Start server!");
        int idClient = 1;

        try (ServerSocket serverSocket = new ServerSocket(Settings.PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ClientThread clientThread = new ClientThread(socket, this, idClient++);
                    clientThread.start();
                } catch (IOException ex) {
                    Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }

        Logger.INSTANCE.log("Stop server!");
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public void sendMessageAll(Message msg) {
        clients.forEach((key, value) -> value.getClientThread().sendMessage(msg));
    }
}
