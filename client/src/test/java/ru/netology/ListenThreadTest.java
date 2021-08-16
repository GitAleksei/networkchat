package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ListenThreadTest {

    @Test
    void runTestNPE() {
        ListenThread listenThread = new ListenThread(null);
        Assertions.assertThrows(NullPointerException.class, listenThread::run);
    }

    @Test
    void runTestClosedStream() throws IOException {
        BufferedReader in = (new BufferedReader(new InputStreamReader(System.in)));
        ListenThread listenThread =
                new ListenThread(in);
        in.close();
        Assertions.assertDoesNotThrow(listenThread::run);
    }
}