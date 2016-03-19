package com.example.jason.helloworld.fragments;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.common.DateUtil;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Page;
import com.example.jason.helloworld.model.TvShowItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CollectionFragment extends LatestTvShowsFragment {

    public CollectionFragment() {
    }


    @Override
    protected void fetchDataFromInternet(Page page) {
        StringRequest tvShowsDataRequest = new StringRequest(Request.Method.GET, TvShowsUrl.LIST_CHOOSED_TVSHOWS + "/" + MyApplication.user.getId() +
                "?page=" + page.getNumber() + "&size=" + page.getSize() + "&token=90b38c45-7272-4763-9d93-13c2bfaa4f1f", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONArray data = responseJson.getJSONObject("data").getJSONArray("content");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject chooseTvShow = data.getJSONObject(i);
                            JSONObject itemJson = chooseTvShow.getJSONObject("tvShow");
                            TvShowItem tvShowItem = new TvShowItem();
                            tvShowItem.setName(itemJson.getString("name"));
                            tvShowItem.setOriginName(itemJson.getString("originName"));
                            tvShowItem.setShowTime(itemJson.getString("showTime"));
                            tvShowItem.setDescribe(itemJson.getString("introduction"));
                            tvShowItem.setType(itemJson.getString("type"));
                            tvShowItem.setShowPlatform(itemJson.getString("showPlatform"));
                            tvShowItem.setCollectedTime(DateUtil.TimeMillisToStr(chooseTvShow.getLong("addTime")));
                            String prePicUrl = itemJson.getString("picture");
                            tvShowItem.setPicUrl(TvShowsUrl.PIC_URL + prePicUrl.substring(prePicUrl.lastIndexOf("/") + 1));
                            tvShowItems.add(tvShowItem);
                            //TODO add the bind the data from server to page.
                        }
                        Log.i("tvShowItems", tvShowItems.toString());
                    } else {
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0);
            }
        });
        requestQueue.add(tvShowsDataRequest);
    }
}
