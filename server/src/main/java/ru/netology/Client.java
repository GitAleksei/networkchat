package ru.netology;

public class Client {
    private final int id;
    private final String name;
    private final ClientThread clientThread;

    public Client(int id, String name, ClientThread clientThread) {
        this.id = id;
        this.name = name;
        this.clientThread = clientThread;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }
}
