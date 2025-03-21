package com.roy.rabbitmq;

import java.util.UUID;

/**
 * @author chenxuegui
 * @since 2025/3/20
 */
public class Message {

    private String data;

    private String id = UUID.randomUUID().toString();

    private long time = System.currentTimeMillis();
    public Message(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", id='" + id + '\'' +
                ", time=" + time +
                '}';
    }
}
