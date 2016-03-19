package com.example.jason.helloworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends UploadActivity implements View.OnClickListener {
    private LinearLayout LLPortrait, LLUsername, LLEmail, LLChangePwd;
    private ImageView IVPortrait;
    private TextView TVUsername, TVEmail;
    private Button backBtn, logoutBtn;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private User user;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateUser(user);
            bindDataToUI(user);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initCompoment();
        setListener();
        user = (User) getIntent().getSerializableExtra("user");
        bindDataToUI(user);
    }

    private void updateUser(final User user) {
        StringRequest updateUserRequest = new StringRequest(Request.Method.POST, TvShowsUrl.USER_INFO_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseJson = null;
                try {
                    responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 0) {
                        Toast.makeText(UserInfoActivity.this, "更新失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserInfoActivity.this, "请检查网络!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("portraitUrl", getServerImgUrl());
                map.put("username", user.getUsername());
                map.put("email", user.getEmail());
                map.put("id", user.getId() + "");
                return map;
            }
        };
        requestQueue.add(updateUserRequest);
    }

    private void bindDataToUI(User user) {
        TVUsername.setText(user.getUsername());
        TVEmail.setText(user.getEmail());
        loadUserPortraitImg(user);
    }

    private void initCompoment() {
        requestQueue = Volley.newRequestQueue(UserInfoActivity.this);
        imageLoader = new ImageLoader(requestQueue, MyApplication.getInstance().getBitmapCache());
    }

    private void loadUserPortraitImg(User user) {
        ImageLoader.ImageListener portraitListener = ImageLoader.getImageListener(IVPortrait, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(user.getPortraitUrl(), portraitListener);
    }

    private void setListener() {
        LLPortrait.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        LLEmail.setOnClickListener(this);
        LLChangePwd.setOnClickListener(this);
    }

    @Override
    public ImageView getImageView() {
        return IVPortrait;
    }

    @Override
    public Context getContext() {
        return UserInfoActivity.this;
    }

    @Override
    public void afterUpload() {
        myHandler.sendEmptyMessage(1);
    }

    private void initView() {
        LLPortrait = (LinearLayout) this.findViewById(R.id.user_info_portrait_line);
        LLUsername = (LinearLayout) this.findViewById(R.id.user_info_username_line);
        LLEmail = (LinearLayout) this.findViewById(R.id.user_info_email_line);
        LLChangePwd = (LinearLayout) this.findViewById(R.id.user_info_change_pwd);
        TVUsername = (TextView) this.findViewById(R.id.user_info_username);
        TVEmail = (TextView) this.findViewById(R.id.user_info_email);
        IVPortrait = (ImageView) this.findViewById(R.id.user_info_portrait);
        backBtn = (Button) this.findViewById(R.id.user_info_back_button);
        logoutBtn = (Button) this.findViewById(R.id.user_info_logout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_portrait_line:
                showDialog();
                break;
            case R.id.user_info_email_line:
                startActivity(new Intent(UserInfoActivity.this, ModifyEmailActivity.class).putExtra("user", user));
                break;
            case R.id.user_info_change_pwd:
                startActivity(new Intent(UserInfoActivity.this, ModifyPwdActivity.class).putExtra("user", user));
                break;
            case R.id.user_info_back_button:
                finish();
                break;
            case R.id.user_info_logout:
                break;
        }
    }

}
