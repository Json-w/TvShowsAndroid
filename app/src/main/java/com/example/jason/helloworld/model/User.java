package com.example.jason.helloworld.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String portraitUrl;

    public User() {
    }

    public User(int id, String username, String password, String portraitUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.portraitUrl = portraitUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }
}
