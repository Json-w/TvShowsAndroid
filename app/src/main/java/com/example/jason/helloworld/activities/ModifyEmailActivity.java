package com.example.jason.helloworld.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ModifyEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backBtn, finishBtn;
    private TextView titleTV;
    private EditText modifyContentET;
    private RequestQueue requestQueue;
    private User user;
    private ProgressDialog pd;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            finish();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_email);
        requestQueue = Volley.newRequestQueue(this);
        initView();
        user = (User) getIntent().getSerializableExtra("user");
        modifyContentET.setText(user.getEmail());
        backBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
    }

    private void initView() {
        backBtn = (Button) this.findViewById(R.id.modify_back_button);
        finishBtn = (Button) this.findViewById(R.id.modify_finish_button);
        titleTV = (TextView) this.findViewById(R.id.modify_title);
        modifyContentET = (EditText) this.findViewById(R.id.modify_content);
    }

    private void updateUser(final User user) {
        StringRequest updateUserRequest = new StringRequest(Request.Method.POST, TvShowsUrl.USER_INFO_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseJson = null;
                try {
                    responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 0) {
                        Toast.makeText(ModifyEmailActivity.this
                                , "更新失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myHandler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyEmailActivity.this, "请检查网络!", Toast.LENGTH_SHORT).show();
                myHandler.sendEmptyMessage(0);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("portraitUrl", user.getPortraitUrl());
                map.put("username", user.getUsername());
                map.put("email", modifyContentET.getText().toString());
                map.put("id", user.getId() + "");
                return map;
            }
        };
        requestQueue.add(updateUserRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_finish_button:
                pd = ProgressDialog.show(ModifyEmailActivity.this, null, "正在更新，请稍候...");
                pd.show();
                updateUser(user);
                break;
            case R.id.modify_back_button:
                finish();
                break;
        }
    }
}
