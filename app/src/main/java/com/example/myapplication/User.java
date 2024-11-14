package com.example.myapplication;

public class User {
    private String username;
    private String password;

    public User() {
        // Constructeur vide requis par Firebase
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}