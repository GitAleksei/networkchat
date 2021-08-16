package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {

    @Test
    void newTestNPE() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new ClientThread(null, null, 1));
    }
}