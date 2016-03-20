package com.example.jason.helloworld.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.jason.helloworld.common.DateUtil;
import com.example.jason.helloworld.common.TvShowsUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendActivity extends UploadActivity {
    private Button sendBtn, backBtn;
    private ImageView sendImg;
    private EditText ETsendContent;
    private Context mContext;
    private static ProgressDialog pd;
    private RequestQueue requestQueue;
    private String serverImgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        requestQueue = Volley.newRequestQueue(this);
        mContext = SendActivity.this;
        initView();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sendActivityRequest = new StringRequest(Request.Method.POST, TvShowsUrl.SEND_ACTIVITY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            if (responseJson.getInt("statusCode") == 1) {
                                myHandler.sendEmptyMessage(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myHandler.sendEmptyMessage(2);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        return getInput();
                    }
                };
                requestQueue.add(sendActivityRequest);
                pd = ProgressDialog.show(mContext, null, "正在发布动态，请稍候...");
                pd.show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    public ImageView getImageView() {
        return sendImg;
    }

    @Override
    public Context getContext() {
        return SendActivity.this;
    }

    @Override
    public void afterUpload() {
        serverImgUrl = getServerImgUrl();
    }

    private Map<String, String> getInput() {
        Map<String, String> map = new HashMap<>();
        map.put("content", ETsendContent.getText().toString());
        map.put("releaseTime", DateUtil.dateToStr(new Date()));
        map.put("userId", MyApplication.user.getId() + "");
        map.put("picUrl", serverImgUrl);
        return map;
    }

    private void initView() {
        sendBtn = (Button) this.findViewById(R.id.send_button);
        backBtn = (Button) this.findViewById(R.id.send_back_button);
        sendImg = (ImageView) this.findViewById(R.id.send_img);
        ETsendContent = (EditText) this.findViewById(R.id.send_content);
    }

    Handler myHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "发布失败", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
            return false;
        }
    });
}
