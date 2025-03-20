package com.roy.rabbitmq;

/**
 * @author chenxuegui
 * @since 2025/3/20
 */
public class Message {

    private String data;

    private long time = System.currentTimeMillis();

    public Message(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", time=" + time +
                '}';
    }
}
