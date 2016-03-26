package com.example.jason.helloworld.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.BitmapCache;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Following;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FollowingAdapter extends BaseAdapter {
    private List<Following> datas;
    private ListView listView;
    private ImageLoader imageLoader;
    private BitmapCache bitmapCache;
    private RequestQueue requestQueue;

    public FollowingAdapter(List<Following> datas, RequestQueue requestQueue) {
        this.datas = datas;
        this.requestQueue = requestQueue;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
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
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Following following = datas.get(position);
        ImageLoader.ImageListener portraitImageListener = ImageLoader.getImageListener(viewHolder.portraitImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(following.getFollowingUser().getPortraitUrl(), portraitImageListener);
        viewHolder.username.setText(following.getFollowingUser().getUsername());
        viewHolder.followingBtn.setText("已关注");
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.followingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("确定不再关注？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelFollow(following.getId(), finalViewHolder.followingBtn);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        return convertView;
    }

    private void cancelFollow(int id, final Button followingBtn) {
        StringRequest cancelFollowRequest = new StringRequest(Request.Method.DELETE, TvShowsUrl.CANCEL_FOLLOW + "/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (new JSONObject(response).getInt("statusCode") == 1) {
                        followingBtn.setText("关注");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(cancelFollowRequest);
    }

    class ViewHolder {
        ImageView portraitImg;
        TextView username;
        Button followingBtn;
    }
}
