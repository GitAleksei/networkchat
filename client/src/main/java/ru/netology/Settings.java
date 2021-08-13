package ru.netology;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE_NAME =
            "client/src/main/resources/settings_client.properties";

    public static int PORT;
    public static String START_MESSAGE_NEW_CLIENT;
    public static String EXIT_MESSAGE;
    public static String SERVER_NAME;

    static {
        Properties properties = new Properties();

        try (FileReader fileReader = new FileReader(SETTINGS_FILE_NAME)) {
            properties.load(fileReader);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            START_MESSAGE_NEW_CLIENT = properties.getProperty("START_MESSAGE_NEW_CLIENT");
            EXIT_MESSAGE = properties.getProperty("EXIT_MESSAGE");
            SERVER_NAME = properties.getProperty("SERVER_NAME");

            Logger.INSTANCE.log("Read \"" + SETTINGS_FILE_NAME + "\"");
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }
}
