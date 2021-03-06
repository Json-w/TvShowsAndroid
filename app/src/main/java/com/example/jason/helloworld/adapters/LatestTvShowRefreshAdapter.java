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
import com.example.jason.helloworld.model.TvShowItem;

import java.util.List;

public class LatestTvShowRefreshAdapter extends BaseAdapter {
    private List<TvShowItem> datas;
    private ListView listView;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;

    public LatestTvShowRefreshAdapter(List<TvShowItem> datas, RequestQueue requestQueue) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_tvshows_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvShowPic = (ImageView) convertView.findViewById(R.id.item_tvShowPic);
            viewHolder.txName = (TextView) convertView.findViewById(R.id.item_tvShowName);
            viewHolder.txShowTime = (TextView) convertView.findViewById(R.id.item_tvShowTime);
            viewHolder.txDescribe = (TextView) convertView.findViewById(R.id.item_describe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TvShowItem tvShowItem = datas.get(position);
        viewHolder.txName.setText(tvShowItem.getName() + "(" + tvShowItem.getOriginName() + ")");
        viewHolder.txShowTime.setText(tvShowItem.getShowTime());
        viewHolder.txDescribe.setText("\t\t" + tvShowItem.getDescribe());
        if (tvShowItem.getPicUrl() != null && !tvShowItem.getPicUrl().equals("")) {
            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.tvShowPic, R.mipmap.icon, R.mipmap.load_image_failed);
            imageLoader.get(tvShowItem.getPicUrl(), imageListener);
        }
        return convertView;
    }

    class ViewHolder {

        ImageView tvShowPic;
        TextView txName, txShowTime, txDescribe;
    }
}
