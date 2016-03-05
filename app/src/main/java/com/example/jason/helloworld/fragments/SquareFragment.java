package com.example.jason.helloworld.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.example.jason.helloworld.model.User;
import com.example.jason.helloworld.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SquareFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView xListView;
    private ActivitiesRefreshAdapter activitiesRefreshAdapter;
    private List<TvShowActivity> datas = new ArrayList<>();
    private RequestQueue requestQueue;

    public SquareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.square_fragment, container, false);
        xListView = (XListView) view.findViewById(R.id.square_fragment_activity_list);
        requestQueue = Volley.newRequestQueue(getContext());
        activitiesRefreshAdapter = new ActivitiesRefreshAdapter(datas, requestQueue);
        xListView.setAdapter(activitiesRefreshAdapter);
        xListView.setXListViewListener(this);
        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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

                        }
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
    }

    private User fetchUserInfoFromInternet(int userId) {
        final User user = new User();
        StringRequest fetUserInfoRequest = new StringRequest(TvShowsUrl.USER_INFO_URL + userId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONObject data = responseJson.getJSONObject("data");
                        user.setId(data.getInt("id"));
                        user.setUsername(data.getString("username"));
                        user.setFollowerId(data.getInt("followerId"));
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
        return user;
    }

}
