package ru.netology;

import java.util.Date;

public class Message {
    private final String name;
    private final String text;
    private final Date date;

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
