package ru.netology.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE_NAME = "settings_server.txt";

    public static int PORT;

    static {
        Properties properties = new Properties();

        try (FileReader fileReader = new FileReader(SETTINGS_FILE_NAME)) {
            properties.load(fileReader);

            PORT = Integer.parseInt(properties.getProperty("PORT"));

            Logger.INSTANCE.log("Read \"" + SETTINGS_FILE_NAME + "\"");
        } catch (IOException ex) {
            Logger.INSTANCE.log(Arrays.toString(ex.getStackTrace()) + " " + ex.getMessage());
        }
    }
}
