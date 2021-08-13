package ru.netology;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class ListenThread extends Thread {
    private final BufferedReader in;

    public ListenThread(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String strMessage = in.readLine();

                Gson gson = new Gson();
                Message message = gson.fromJson(strMessage, Message.class);

                Logger.INSTANCE.log(message.toString());
                System.out.println(message);
            }
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        } finally {
            Logger.INSTANCE.log("Поток прослушки входных сообщений завершен. ListenThread");
        }
    }
}
