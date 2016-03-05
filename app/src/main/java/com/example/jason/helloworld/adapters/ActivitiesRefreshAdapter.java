package com.example.jason.helloworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.TvShowActivity;

import java.util.List;

public class ActivitiesRefreshAdapter extends BaseAdapter {
    private List<TvShowActivity> datas;
    private ListView listView;
    private ImageLoader imageLoader;

    public ActivitiesRefreshAdapter(List<TvShowActivity> datas, RequestQueue requestQueue) {
        this.datas = datas;
        BitmapCache bitmapCache = MyApplication.getInstance().getBitmapCache();
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_item, null);
            viewHolder = new ViewHolder();
            viewHolder.IVActivityItemPortrait = (ImageView) convertView.findViewById(R.id.activity_item_portrait_img);
            viewHolder.IVActivityItemImg = (ImageView) convertView.findViewById(R.id.activity_item_img);
            viewHolder.txUsername = (TextView) convertView.findViewById(R.id.activity_item_username);
            viewHolder.txActivityContent = (TextView) convertView.findViewById(R.id.activity_item_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TvShowActivity tvShowActivity = datas.get(position);
        viewHolder.txUsername.setText(tvShowActivity.getUsername());
        viewHolder.txActivityContent.setText(tvShowActivity.getContent());

        ImageLoader.ImageListener contentImageListener = ImageLoader.getImageListener(viewHolder.IVActivityItemImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        ImageLoader.ImageListener portraitImageListener = ImageLoader.getImageListener(viewHolder.IVActivityItemPortrait, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(tvShowActivity.getPictureUrl(), contentImageListener);
        imageLoader.get(tvShowActivity.getUserPortraitUrl(), portraitImageListener);
        return convertView;
    }

    class ViewHolder {

        ImageView IVActivityItemPortrait, IVActivityItemImg;
        TextView txUsername, txActivityContent;
    }
}
