package com.example.jason.helloworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.model.TvShowActivity;

import java.util.List;

public class PersonalGridViewAdapter extends BaseAdapter {
    private List<TvShowActivity> datas;
    private BitmapCache bitmapCache;
    private ImageLoader imageLoader;

    public PersonalGridViewAdapter(List<TvShowActivity> datas, RequestQueue requestQueue) {
        this.datas = datas;
        bitmapCache = MyApplication.getInstance().getBitmapCache();
        imageLoader = new ImageLoader(requestQueue, bitmapCache);
    }

    @Override
    public int getCount() {
        return datas.size() + 1;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item, null);
            viewHolder.IVActivity = (ImageView) convertView.findViewById(R.id.gridview_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.IVActivity.setImageResource(R.mipmap.add_pic);
        } else {
            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.IVActivity, R.drawable.ic_launcher, R.drawable.ic_launcher);
            TvShowActivity tvShowActivity = datas.get(position-1);
            imageLoader.get(tvShowActivity.getPictureUrl(), imageListener);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView IVActivity;
    }
}
