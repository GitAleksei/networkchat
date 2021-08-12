package ru.netology.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private Map<Integer, Client> clients = new ConcurrentHashMap<>();

    public Server() {
        Logger.INSTANCE.log("Start server!");
        int idClients = 1;

        try (ServerSocket serverSocket = new ServerSocket(Settings.PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ClientThread clientThread = new ClientThread(socket, this);
                    Client client = new Client(idClients++, clientThread);
                    clients.put(client.getId(), client);

                    client.getClientThread().start();

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
