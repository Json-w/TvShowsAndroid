package com.example.jason.helloworld.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private String content;
    private User commentUser;

    public Comment(int id, String content, User commentUser) {
        this.id = id;
        this.content = content;
        this.commentUser = commentUser;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(User commentUser) {
        this.commentUser = commentUser;
    }
}
