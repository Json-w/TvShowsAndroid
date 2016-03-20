package com.example.jason.helloworld.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.TvShowItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView IVDetailTvShow;
    private TextView TVName, TVPlatform, TVShowTime, TVType, TVDescribe;
    private Button backBtn, favoraiteBtn;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;
    private TvShowItem tvShowItem;
    private ProgressDialog pd;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (pd != null) {
                pd.dismiss();
            }
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    favoraiteBtn.setText("取消收藏");
                    favoraiteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pd = ProgressDialog.show(DetailTvShowActivity.this, null, "正在收藏,请稍候...");
                            pd.show();
                            try {
                                cancleCollectedTvShow();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case 2:
                    favoraiteBtn.setText("收藏");
                    favoraiteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pd = ProgressDialog.show(DetailTvShowActivity.this, null, "正在收藏,请稍候...");
                            pd.show();
                            try {
                                collectTvShow();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshow_detail);
        bitmapCache = MyApplication.getInstance().getBitmapCache();
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, bitmapCache);
        initView();
        backBtn.setOnClickListener(this);
        favoraiteBtn.setOnClickListener(this);
        tvShowItem = (TvShowItem) getIntent().getSerializableExtra("tvShowItem");
        TVName.setText(tvShowItem.getName() + "(" + tvShowItem.getOriginName() + ")");
        TVPlatform.setText(tvShowItem.getShowPlatform());
        TVShowTime.setText(tvShowItem.getShowTime());
        TVType.setText(tvShowItem.getType());
        TVDescribe.setText("\t\t" + tvShowItem.getDescribe());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(IVDetailTvShow, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(tvShowItem.getPicUrl(), imageListener);
        try {
            checkIfCollected();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        IVDetailTvShow = (ImageView) this.findViewById(R.id.detail_tvshow_img);
        TVName = (TextView) this.findViewById(R.id.detail_tvshow_name);
        TVPlatform = (TextView) this.findViewById(R.id.detail_tvshow_showPlatform);
        TVShowTime = (TextView) this.findViewById(R.id.detail_tvshow_time);
        TVType = (TextView) this.findViewById(R.id.detail_tvshow_type);
        TVDescribe = (TextView) this.findViewById(R.id.detail_tvshow_desc);
        backBtn = (Button) this.findViewById(R.id.tvShow_detail_back_button);
        favoraiteBtn = (Button) this.findViewById(R.id.detail_tvshow_favoriate_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvShow_detail_back_button:
                finish();
                break;
            case R.id.detail_tvshow_favoriate_btn:
                pd = ProgressDialog.show(DetailTvShowActivity.this, null, "正在收藏,请稍候...");
                pd.show();
                try {
                    collectTvShow();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void collectTvShow() throws UnsupportedEncodingException {
        StringRequest collectTvShowRequest = new StringRequest(bindParamsToUrl(TvShowsUrl.COLLECT_TVSHOW_URL,
                new String[]{tvShowItem.getName(), MyApplication.user.getId() + ""}), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (new JSONObject(response).getInt("statusCode") == 1) {
                        Toast.makeText(DetailTvShowActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        myHandler.sendEmptyMessage(1);
                    } else {
                        Toast.makeText(DetailTvShowActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                        myHandler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailTvShowActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                myHandler.sendEmptyMessage(0);
            }
        });
        requestQueue.add(collectTvShowRequest);
    }

    private String bindParamsToUrl(String baseUrl, String[] pathParams) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(baseUrl);
        for (String param : pathParams) {
            sb.append("/" + URLEncoder.encode(param, "utf-8"));
        }
        return sb.toString();
    }

    private void checkIfCollected() throws UnsupportedEncodingException {
        StringRequest checkIfCollectedRequest = new StringRequest(bindParamsToUrl(TvShowsUrl.CHECK_IF_TVSHOW_COLLECTED_URL,
                new String[]{tvShowItem.getName(), MyApplication.user.getId() + ""}), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        if (responseJson.getJSONObject("data").getBoolean("result")) {
                            myHandler.sendEmptyMessage(1);
                        }
                    } else {
                        myHandler.sendEmptyMessage(0);
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
        });
        requestQueue.add(checkIfCollectedRequest);
    }

    private void cancleCollectedTvShow() throws UnsupportedEncodingException {
        StringRequest cancleCollectedTvShow = new StringRequest(bindParamsToUrl(TvShowsUrl.CANCLE_COLLECT_TVSHOW_URL,
                new String[]{tvShowItem.getName(), MyApplication.user.getId() + ""}), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (new JSONObject(response).getInt("statusCode") == 1) {
                        myHandler.sendEmptyMessage(2);
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
        });
        requestQueue.add(cancleCollectedTvShow);
    }
}
