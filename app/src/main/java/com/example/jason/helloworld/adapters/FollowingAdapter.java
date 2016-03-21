package com.example.jason.helloworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.Following;

import java.util.List;

public class FollowingAdapter extends BaseAdapter {
    private List<Following> datas;
    private ListView listView;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;

    public FollowingAdapter(List<Following> datas, RequestQueue requestQueue) {
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
        if (listView == null) {
            listView = (ListView) parent;
        }
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_item, null);
            viewHolder = new ViewHolder();
            viewHolder.portraitImg = (ImageView) convertView.findViewById(R.id.followers_portrait);
            viewHolder.username = (TextView) convertView.findViewById(R.id.followers_username);
            viewHolder.followingBtn = (Button) convertView.findViewById(R.id.followers_btn);
            convertView.setTag(viewHolder);
        } else {
            convertView.getTag();
        }
        Following following = datas.get(position);
        ImageLoader.ImageListener portraitImageListener = ImageLoader.getImageListener(viewHolder.portraitImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(following.getFollowingUser().getPortraitUrl(), portraitImageListener);
        viewHolder.username.setText(following.getFollowingUser().getUsername());
        viewHolder.followingBtn.setText("following");
        return convertView;
    }

    class ViewHolder {
        ImageView portraitImg;
        TextView username;
        Button followingBtn;
    }
}
