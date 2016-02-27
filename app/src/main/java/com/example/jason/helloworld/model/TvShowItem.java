package com.example.jason.helloworld.model;

import java.io.Serializable;

public class TvShowItem implements Serializable {
    private String name;
    private String originName;
    private String showTime;
    private String describe;
    private String picUrl;
    private String showPlatform;
    private String type;

    public TvShowItem() {
    }

    public TvShowItem(String name, String originName, String showTime, String describe, String picUrl, String showPlatform, String type) {

        this.name = name;
        this.originName = originName;
        this.showTime = showTime;
        this.describe = describe;
        this.picUrl = picUrl;
        this.showPlatform = showPlatform;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShowPlatform() {
        return showPlatform;
    }

    public void setShowPlatform(String showPlatform) {
        this.showPlatform = showPlatform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
