package com.example.jason.helloworld;

import android.app.Activity;
import android.app.Application;

import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyApplication extends Application {
    private String token;
    private static MyApplication instance;
    private BitmapCache bitmapCache;
    public static User user;
    private static Map<String, Activity> destoryMap = new HashMap<>();

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

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    public static void destroyAddedActivity() {
        for (String key : destoryMap.keySet()) {
            destoryMap.get(key).finish();
        }
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }
}
