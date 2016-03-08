package com.example.jason.helloworld.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.adapters.ActivitiesRefreshAdapter;
import com.example.jason.helloworld.common.DateUtil;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Page;
import com.example.jason.helloworld.model.TvShowActivity;
import com.example.jason.helloworld.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SquareFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView xListView;
    private ActivitiesRefreshAdapter activitiesRefreshAdapter;
    private List<TvShowActivity> datas = new ArrayList<>();
    private RequestQueue requestQueue;
    private Page page = new Page();
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            activitiesRefreshAdapter.notifyDataSetChanged();
            onLoad();
            super.handleMessage(msg);
        }
    };

    public SquareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.square_fragment, container, false);
        xListView = (XListView) view.findViewById(R.id.square_fragment_activity_list);
        requestQueue = Volley.newRequestQueue(getContext());
        fetchDataFromInternet(page);
        activitiesRefreshAdapter = new ActivitiesRefreshAdapter(datas, requestQueue);
        xListView.setAdapter(activitiesRefreshAdapter);
        xListView.setXListViewListener(this);
        return view;
    }



    @Override
    public void onRefresh() {
        datas.clear();
        fetchDataFromInternet(new Page());
        activitiesRefreshAdapter = new ActivitiesRefreshAdapter(datas, requestQueue);
        xListView.setAdapter(activitiesRefreshAdapter);
    }

    @Override
    public void onLoadMore() {
        if (page.isLast()) {
            Toast.makeText(getContext(), "没有数据了", Toast.LENGTH_SHORT).show();
            myHandler.sendEmptyMessage(0);
        } else {
            int number = page.getNumber() + 1;
            page.setNumber(number);
            fetchDataFromInternet(page);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void fetchDataFromInternet(final Page page) {
        StringRequest fetchDataRequest = new StringRequest(TvShowsUrl.ACTIVITIES_GET_ALL + "?page=" + page.getNumber() + "&size=" + page.getSize(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONObject data = responseJson.getJSONObject("data");
                        page.setLast(data.getBoolean("last"));
                        JSONArray content = data.getJSONArray("content");
                        for (int i = 0; i < content.length(); i++) {
                            JSONObject activityJson = (JSONObject) content.get(i);
                            TvShowActivity tvShowActivity = new TvShowActivity();
                            tvShowActivity.setId(activityJson.getInt("id"));
                            tvShowActivity.setUserId(activityJson.getInt("userId"));
                            tvShowActivity.setContent(activityJson.getString("content"));
                            tvShowActivity.setPictureUrl(TvShowsUrl.BASE_URL + activityJson.getString("picUrl"));
                            tvShowActivity.setReleaseTime(DateUtil.TimeMillisToStr(activityJson.getLong("releaseTime")));
                            JSONObject user = activityJson.getJSONObject("user");
                            tvShowActivity.setUsername(user.getString("username"));
                            tvShowActivity.setUserPortraitUrl(TvShowsUrl.BASE_URL + user.getString("portraitUrl"));
                            datas.add(tvShowActivity);
                        }
                    }
                    myHandler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                myHandler.sendEmptyMessage(0);
            }
        });
        requestQueue.add(fetchDataRequest);
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
