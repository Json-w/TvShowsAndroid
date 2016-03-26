package com.example.jason.helloworld.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.model.User;


public class WelcomeActivity extends AppCompatActivity {
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (userInfo.getString("token", "").equals("")) {
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
            }, 3000);
            finish();
            return;
        }
        if (userInfo != null) {

        }
        MyApplication.user = getUserFromSharedPreferences(userInfo);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        }, 3000);
        finish();
    }

    @NonNull
    private User getUserFromSharedPreferences(SharedPreferences userInfo) {
        User user = new User();
        user.setId(userInfo.getInt("id", -1));
        user.setUsername(userInfo.getString("username", ""));
        user.setPortraitUrl(userInfo.getString("portraitUrl", ""));
        user.setEmail(userInfo.getString("email", ""));
        return user;
    }
}
