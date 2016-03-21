package com.example.jason.helloworld.model;

import java.io.Serializable;

public class Follower implements Serializable {
    private int id;
    private User user;
    private User follower;
    private boolean ifFollowing;

    public Follower(int id, User user, User follower) {
        this.id = id;
        this.user = user;
        this.follower = follower;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
