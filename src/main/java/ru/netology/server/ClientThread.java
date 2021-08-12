package ru.netology.server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class ClientThread extends Thread {
    private final Socket socket;
    private final Server server;
    private BufferedReader in;
    private PrintWriter out;
    private final int idClient;

    public ClientThread(Socket socket, Server server, int idClient)
    {
        this.socket = socket;
        this.server = server;
        this.idClient = idClient;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Logger.INSTANCE.log("Client connected: " + socket);

            while (true) {
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line);
                }

                Gson gson = new Gson();
                Message message = gson.fromJson(stringBuilder.toString(), Message.class);

                if (message.getText().equals(Settings.START_MESSAGE_NEW_CLIENT)) {
                    Logger.INSTANCE.log("Пользователь " + message.getName() +
                            " добавился в чат");

                    server.getClients().put(idClient,
                            new Client(idClient, message.getName(), this));

                    server.sendMessageAll(
                            new Message("Server", message.getName() + " добавился в чат",
                                    new Date()));
                    continue;
                }

                if (message.getText().equals(Settings.EXIT_MESSAGE)) {
                    Client client = server.getClients().get(idClient);
                    Logger.INSTANCE.log("Пользователь " + client.getName() + " покинул чат");
                    server.sendMessageAll(
                            new Message("Server", client.getName() + " покинул чат",
                            new Date()));
                    server.getClients().remove(idClient);
                    break;
                }

                Logger.INSTANCE.log(message.toString());
                server.sendMessageAll(message);
            }

        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }

    public void sendMessage(Message message) {
        try {
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
        } catch (Exception ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }
}
