// Message.java - Updated with sortingScore
package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String content;
    private String username;
    private long timestamp;
    private int likes;
    private String key;
    private double sortingScore; // Nouvelle propriété pour le tri
    private List<String> likedByUsers;

    public Message() {
        likedByUsers = new ArrayList<>();
    }

    public Message(String content, String username, int likes) {
        this.content = content;
        this.username = username;
        this.likes = likes;
        this.timestamp = System.currentTimeMillis();
        this.likedByUsers = new ArrayList<>();
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

    public List<String> getLikedByUsers() {
        return likedByUsers;
    }

    public double getSortingScore() {
        return sortingScore;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLikedByUsers(List<String> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public void setSortingScore(double sortingScore) {
        this.sortingScore = sortingScore; // Setter pour sortingScore
    }
}
