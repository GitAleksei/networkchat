package ru.netology.server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread extends Thread {
    private final Socket socket;
    private final Server server;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket, Server server)
    {
        this.socket = socket;
        this.server = server;

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

                Logger.INSTANCE.log(message.toString());

                if (message.getText().equals("/exit")) {
                    break;
                }

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
