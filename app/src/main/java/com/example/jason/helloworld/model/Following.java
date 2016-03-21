package com.example.jason.helloworld.model;

import java.io.Serializable;

public class Following implements Serializable {
    private int id;
    private User user;
    private User followingUser;

    public Following() {
    }

    public Following(int id, User user, User followingUser) {
        this.id = id;
        this.user = user;
        this.followingUser = followingUser;
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

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }
}
