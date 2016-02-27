package com.example.jason.helloworld.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.TvShowItem;

public class DetailTvShowActivity extends AppCompatActivity {
    private ImageView IVDetailTvShow;
    private TextView TVName, TVPlatform, TVShowTime, TVType, TVDescribe;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshow_detail);
        bitmapCache = MyApplication.getInstance().getBitmapCache();
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, bitmapCache);
        initView();
        TvShowItem tvShowItem = (TvShowItem) getIntent().getSerializableExtra("tvShowItem");
        TVName.setText(tvShowItem.getName() + "(" + tvShowItem.getOriginName() + ")");
        TVPlatform.setText(tvShowItem.getShowPlatform());
        TVShowTime.setText(tvShowItem.getShowTime());
        TVType.setText(tvShowItem.getType());
        TVDescribe.setText("\t\t" + tvShowItem.getDescribe());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(IVDetailTvShow, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(tvShowItem.getPicUrl(), imageListener);
    }

    private void initView() {
        IVDetailTvShow = (ImageView) this.findViewById(R.id.detail_tvshow_img);
        TVName = (TextView) this.findViewById(R.id.detail_tvshow_name);
        TVPlatform = (TextView) this.findViewById(R.id.detail_tvshow_showPlatform);
        TVShowTime = (TextView) this.findViewById(R.id.detail_tvshow_time);
        TVType = (TextView) this.findViewById(R.id.detail_tvshow_type);
        TVDescribe = (TextView) this.findViewById(R.id.detail_tvshow_desc);
    }
}
