package com.example.jason.helloworld.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.Follower;

import java.util.List;

public class FollowersAdapter extends BaseAdapter {
    private List<Follower> datas;
    private ListView listView;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;

    public FollowersAdapter(List<Follower> datas, RequestQueue requestQueue) {
        this.datas = datas;
        bitmapCache = MyApplication.getInstance().getBitmapCache();
        imageLoader = new ImageLoader(requestQueue, bitmapCache);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
