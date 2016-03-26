package com.example.jason.helloworld.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.TvShowActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backBtn, sendBtn;
    private EditText commentET;
    private RequestQueue requestQueue;
    private TvShowActivity tvShowActivity;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(CommentsActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(CommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        requestQueue = Volley.newRequestQueue(this);
        tvShowActivity = (TvShowActivity) getIntent().getSerializableExtra("activity");
        initView();
        backBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    private void initView() {
        backBtn = (Button) this.findViewById(R.id.comment_back_button);
        sendBtn = (Button) this.findViewById(R.id.comment_send_button);
        commentET = (EditText) this.findViewById(R.id.comment_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_back_button:
                finish();
                break;
            case R.id.comment_send_button:
                doComment();
                break;
        }
    }

    private void doComment() {
        StringRequest commentRequest = new StringRequest(Request.Method.POST, TvShowsUrl.COMMENT_ACTIVITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (new JSONObject(response).getInt("statusCode") == 1) {
                        myHandler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myHandler.sendEmptyMessage(0);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("commentUserId", MyApplication.user.getId() + "");
                map.put("activityId", tvShowActivity.getId() + "");
                map.put("commentContent", commentET.getText().toString());
                return map;
            }
        };
        requestQueue.add(commentRequest);
    }
}
