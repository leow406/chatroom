package com.example.myapplication;

public class Message {
    private String content;
    private String username;
    private long timestamp;
    private int likes;
    private String key; // Clé pour référencer le message dans Firebase

    public Message() {
        // Constructeur vide nécessaire pour Firebase
    }

    public Message(String content, String username, int likes) {
        this.content = content;
        this.username = username;
        this.likes = likes;
        this.timestamp = System.currentTimeMillis();
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
