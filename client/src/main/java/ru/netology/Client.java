package ru.netology;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public Client() {
        Logger.INSTANCE.log("Start client!");

        try (Socket socket = new Socket(Settings.SERVER_NAME, Settings.PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Введите ник: ");
            String userName = scanner.nextLine();

            Message message = new Message(userName, Settings.START_MESSAGE_NEW_CLIENT, new Date());
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);

            System.out.println("Наслаждайтесь общением в чате. Для выхода напишите \"" +
                    Settings.EXIT_MESSAGE + "\"");

            ListenThread listenThread = new ListenThread(in);

            while (true) {
                String printingText = scanner.nextLine();

                message = new Message(userName, Settings.EXIT_MESSAGE, new Date());
                jsonMessage = gson.toJson(message);
                out.println(jsonMessage);
                Logger.INSTANCE.log(message.toString());

                if (Settings.EXIT_MESSAGE.equals(printingText)) {
                    listenThread.interrupt();
                     break;
                }
            }

            System.out.println("Клиент прекратил работу!");
            Logger.INSTANCE.log("Клиент прекратил работу!");

        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }

        Logger.INSTANCE.log("Stop client!");
    }
}
