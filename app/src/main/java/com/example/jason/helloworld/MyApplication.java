package com.example.jason.helloworld;

import android.app.Application;

import com.example.jason.helloworld.common.BitmapCache;

public class MyApplication extends Application {
    private String token;
    private static MyApplication instance;
    private BitmapCache bitmapCache;

    @Override
    public void onCreate() {
        super.onCreate();
        bitmapCache = new BitmapCache();
        instance = this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }
}
