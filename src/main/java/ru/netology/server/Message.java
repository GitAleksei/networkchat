package ru.netology.server;

import java.util.Date;
import java.util.List;

public class Message {
    private String name;
    private String text;
    private Date date;

    public Message(String name, String text, Date date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "{" + name + "} " + text + " [" + date + "]";
    }
}
