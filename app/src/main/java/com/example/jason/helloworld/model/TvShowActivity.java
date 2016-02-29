package com.example.jason.helloworld.model;

import java.io.Serializable;

public class TvShowActivity implements Serializable {
    private int id;
    private int userId;
    private String pictureUrl;
    private String releaseTime;
    private String content;

    public TvShowActivity() {
    }

    public TvShowActivity(int id, int userId, String pictureUrl, String releaseTime, String content) {
        this.id = id;
        this.userId = userId;
        this.pictureUrl = pictureUrl;
        this.releaseTime = releaseTime;
        this.content = content;
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
}
