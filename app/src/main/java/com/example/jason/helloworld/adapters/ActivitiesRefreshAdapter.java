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
    private BitmapCache bitmapCache;

    public ActivitiesRefreshAdapter(List<TvShowActivity> datas, RequestQueue requestQueue) {
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        TvShowItem tvShowItem = datas.get(position);
//        viewHolder.txName.setText(tvShowItem.getName() + "(" + tvShowItem.getOriginName() + ")");
//        viewHolder.txShowTime.setText(tvShowItem.getShowTime());
//        viewHolder.txDescribe.setText(tvShowItem.getDescribe());
//        if (tvShowItem.getPicUrl() != null && !tvShowItem.getPicUrl().equals("")) {
//            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.tvShowPic, R.drawable.ic_launcher, R.drawable.ic_launcher);
//            imageLoader.get(tvShowItem.getPicUrl(), imageListener);
//        }
        return convertView;
    }

    class ViewHolder {

        ImageView tvShowPic;
        TextView txName, txShowTime, txDescribe;
    }
}
