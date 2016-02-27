package com.example.jason.helloworld.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.activities.DetailTvShowActivity;
import com.example.jason.helloworld.adapters.LatestTvShowRefreshAdapter;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Page;
import com.example.jason.helloworld.model.TvShowItem;
import com.example.jason.helloworld.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LatestTvShowsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private LatestTvShowRefreshAdapter myAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private List<TvShowItem> tvShowItems = new ArrayList<>();
    private int mIndex = 0;
    private int mRefreshIndex = 0;
    private RequestQueue requestQueue;
    private Page page = new Page();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myAdapter.notifyDataSetChanged();
            onLoad();
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_list_view, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        fetchDataFromInternet(page);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mListView = (XListView) view.findViewById(R.id.list_view);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());

        mAdapter = new ArrayAdapter<String>(getContext(), R.layout.vw_list_item, items);
        myAdapter = new LatestTvShowRefreshAdapter(tvShowItems, requestQueue);
        mListView.setOnItemClickListener(this);
//        mListView.setAdapter(mAdapter);
        mListView.setAdapter(myAdapter);
    }

    @Override
    public void onRefresh() {
        tvShowItems.clear();
        page = new Page();
        fetchDataFromInternet(page);
        myAdapter = new LatestTvShowRefreshAdapter(tvShowItems, requestQueue);
        mListView.setAdapter(myAdapter);
//        onLoad();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mIndex = ++mRefreshIndex;
//                items.clear();
//                geneItems();
//                mAdapter = new ArrayAdapter<String>(getContext(), R.layout.vw_list_item,
//                        items);
//                mListView.setAdapter(mAdapter);
//                onLoad();
//            }
//        }, 2500);
    }

    @Override
    public void onLoadMore() {
        if (page.isLast()) {
            Toast.makeText(getContext(), "没有数据了", Toast.LENGTH_SHORT).show();
        } else {
            int number = page.getNumber();
            page.setNumber(++number);
            fetchDataFromInternet(page);
        }
//        onLoad();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                geneItems();
//                mAdapter.notifyDataSetChanged();
//                onLoad();
//            }
//        }, 2500);
    }

    private void fetchDataFromInternet(Page page) {
        StringRequest tvShowsDataRequest = new StringRequest(Request.Method.GET, TvShowsUrl.TVSHOWS_URL + "?page=" + page.getNumber() + "&size=" + page.getSize() + "&token=90b38c45-7272-4763-9d93-13c2bfaa4f1f", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONArray data = responseJson.getJSONObject("data").getJSONArray("content");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject itemJson = data.getJSONObject(i);
                            TvShowItem tvShowItem = new TvShowItem();
                            tvShowItem.setName(itemJson.getString("name"));
                            tvShowItem.setOriginName(itemJson.getString("originName"));
                            tvShowItem.setShowTime(itemJson.getString("showTime"));
                            tvShowItem.setDescribe(itemJson.getString("introduction"));
                            tvShowItem.setType(itemJson.getString("type"));
                            tvShowItem.setShowPlatform(itemJson.getString("showPlatform"));
                            String prePicUrl = itemJson.getString("picture");
                            tvShowItem.setPicUrl(TvShowsUrl.PIC_URL + prePicUrl.substring(prePicUrl.lastIndexOf("/") + 1));
                            tvShowItems.add(tvShowItem);
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

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
        TvShowItem tvShowItem = tvShowItems.get(position - 1);
        Intent intent = new Intent(getActivity(), DetailTvShowActivity.class);
        intent.putExtra("tvShowItem", tvShowItem);
        getActivity().startActivity(intent);
    }
}