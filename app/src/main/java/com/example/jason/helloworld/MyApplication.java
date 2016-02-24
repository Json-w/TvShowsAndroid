package com.example.jason.helloworld;

import android.app.Application;

public class MyApplication extends Application {
    private String token;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
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
}
