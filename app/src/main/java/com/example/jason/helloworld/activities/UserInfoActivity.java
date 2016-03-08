package com.example.jason.helloworld.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.model.User;

public class UserInfoActivity extends UploadActivity implements View.OnClickListener {
    private LinearLayout LLPortrait, LLUsername, LLEmail, LLChangePwd;
    private ImageView IVPortrait;
    private TextView TVUsername, TVEmail;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        User user = (User) getIntent().getSerializableExtra("user");
        TVUsername.setText(user.getUsername());
        TVEmail.setText(user.getEmail());
        requestQueue = Volley.newRequestQueue(UserInfoActivity.this);
        imageLoader = new ImageLoader(requestQueue, MyApplication.getInstance().getBitmapCache());
        ImageLoader.ImageListener portraitListener = ImageLoader.getImageListener(IVPortrait, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(user.getPortraitUrl(), portraitListener);
        LLPortrait.setOnClickListener(this);

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
        getServerImgUrl();
    }

    private void initView() {
        LLPortrait = (LinearLayout) this.findViewById(R.id.user_info_portrait_line);
        LLUsername = (LinearLayout) this.findViewById(R.id.user_info_username_line);
        LLEmail = (LinearLayout) this.findViewById(R.id.user_info_email_line);
        LLChangePwd = (LinearLayout) this.findViewById(R.id.user_info_change_pwd);
        TVUsername = (TextView) this.findViewById(R.id.user_info_username);
        TVEmail = (TextView) this.findViewById(R.id.user_info_email);
        IVPortrait = (ImageView) this.findViewById(R.id.user_info_portrait);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_portrait_line:
                showDialog();
                break;
            case R.id.user_info_username_line:
                break;
            case R.id.user_info_email_line:
                break;
            case R.id.user_info_change_pwd:
                break;
            case R.id.user_info_back_button:
                finish();
                break;
            case R.id.user_info_logout:
                break;
        }
    }

}
