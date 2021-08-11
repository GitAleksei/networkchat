package ru.netology.server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandlingThread extends Thread {
    private final Socket socket;
    private final Server server;

    public ClientHandlingThread(Socket socket, Server server)
    {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

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
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }
}
