package com.example.jason.helloworld.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int followerId;
    private int chooseTvShowId;

    public User() {
    }

    public User(int id, String username, String password, int followerId, int chooseTvShowId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.followerId = followerId;
        this.chooseTvShowId = chooseTvShowId;
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

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getChooseTvShowId() {
        return chooseTvShowId;
    }

    public void setChooseTvShowId(int chooseTvShowId) {
        this.chooseTvShowId = chooseTvShowId;
    }
}
