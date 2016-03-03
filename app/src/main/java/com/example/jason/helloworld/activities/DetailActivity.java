package com.example.jason.helloworld.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.TvShowActivity;

public class DetailActivity extends AppCompatActivity {
    private ImageView IVDetailImg;
    private TextView TVDetailContent;
    private Button backBtn, deleteBtn;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bitmapCache = MyApplication.getInstance().getBitmapCache();
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, bitmapCache);
        initView();
        TvShowActivity tvShowActivity = (TvShowActivity) getIntent().getSerializableExtra("detailActivity");
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(IVDetailImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        TVDetailContent.setText(tvShowActivity.getContent());
        imageLoader.get(tvShowActivity.getPictureUrl(), imageListener);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        IVDetailImg = (ImageView) this.findViewById(R.id.detail_activity_img);
        TVDetailContent = (TextView) this.findViewById(R.id.detail_activity_content);
        backBtn = (Button) this.findViewById(R.id.detail_activity_back_button);
        deleteBtn = (Button) this.findViewById(R.id.detail_activity_delete_button);
    }


}
