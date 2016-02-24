package com.example.jason.helloworld.model;

public class TvShowItem {
    private String name;
    private String originName;
    private String showTime;
    private String describe;

    public TvShowItem() {
    }

    public TvShowItem(String name, String originName, String showTime, String describe) {
        this.name = name;
        this.originName = originName;
        this.showTime = showTime;
        this.describe = describe;
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
}
