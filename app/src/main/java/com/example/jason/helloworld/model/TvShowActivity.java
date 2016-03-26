package com.example.jason.helloworld.model;

import java.io.Serializable;
import java.util.List;

public class TvShowActivity implements Serializable {
    private int id;
    private int userId;
    private String pictureUrl;
    private String releaseTime;
    private String content;
    private String username;
    private String userPortraitUrl;
    private List<Comment> comments;

    public TvShowActivity() {
    }

    public TvShowActivity(int id, int userId, String pictureUrl, String releaseTime, String content, String username, String userPortraitUrl, List<Comment> comments) {
        this.id = id;
        this.userId = userId;
        this.pictureUrl = pictureUrl;
        this.releaseTime = releaseTime;
        this.content = content;
        this.username = username;
        this.userPortraitUrl = userPortraitUrl;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPortraitUrl() {
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
