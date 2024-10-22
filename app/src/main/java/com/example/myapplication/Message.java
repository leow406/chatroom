package com.example.myapplication;

public class Message {
    private String content;
    private String username;
    private long timestamp;
    private int likes;  // Ajout du champ "likes"

    // Constructeur vide requis pour Firebase
    public Message() {
    }

    public Message(String content, String username, int likes) {
        this.content = content;
        this.username = username;
        this.likes = likes;
        this.timestamp = System.currentTimeMillis(); // Utiliser un timestamp en millisecondes
    }

    // Getters et Setters
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
}
//petit poney