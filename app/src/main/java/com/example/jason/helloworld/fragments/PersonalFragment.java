package com.example.jason.helloworld.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.activities.DetailActivity;
import com.example.jason.helloworld.activities.FollowingActivity;
import com.example.jason.helloworld.activities.SendActivity;
import com.example.jason.helloworld.activities.UserInfoActivity;
import com.example.jason.helloworld.adapters.PersonalGridViewAdapter;
import com.example.jason.helloworld.common.DateUtil;
import com.example.jason.helloworld.common.StringUtil;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Page;
import com.example.jason.helloworld.model.TvShowActivity;
import com.example.jason.helloworld.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonalFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private ImageView IVPortrait;
    private TextView TVUsername, TVFollowing, TVFollowers;
    private PersonalGridViewAdapter mAdapter;
    private List<TvShowActivity> datas;
    private Page page = new Page();
    private ImageLoader imageLoader;
    private User user;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_fragment, container, false);
        initView(view);
        datas = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        imageLoader = new ImageLoader(requestQueue, MyApplication.getInstance().getBitmapCache());
        fetchUserInfoAndSetToUI(requestQueue);
        fetchDataFromInternet(requestQueue);
        mAdapter = new PersonalGridViewAdapter(datas, requestQueue);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        IVPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserInfoActivity.class).putExtra("user", user));
            }
        });
        TVFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FollowingActivity.class));
            }
        });
        return view;
    }

    private void fetchUserInfoAndSetToUI(RequestQueue requestQueue) {
        StringRequest userInfoRequest = new StringRequest(TvShowsUrl.USER_INFO_URL + MyApplication.user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONObject userJson = responseJson.getJSONObject("data");
                        TVUsername.setText(userJson.getString("username"));
                        TVFollowers.setText(userJson.getInt("followersNum") + "");
                        TVFollowing.setText(userJson.getInt("followingNum") + "");
                        ImageLoader.ImageListener portraitListener = ImageLoader.getImageListener(IVPortrait, R.drawable.ic_launcher, R.drawable.ic_launcher);
                        String portraitUrl = TvShowsUrl.BASE_URL + userJson.getString("portraitUrl");
                        if (!StringUtil.isNull(portraitUrl)) {
                            imageLoader.get(portraitUrl, portraitListener);
                        }
                        user = new User();
                        user.setId(userJson.getInt("id"));
                        user.setUsername(userJson.getString("username"));
                        user.setPortraitUrl(TvShowsUrl.BASE_URL + userJson.getString("portraitUrl"));
                        user.setEmail(userJson.getString("email"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络有误", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(userInfoRequest);
    }

    private void fetchDataFromInternet(RequestQueue requestQueue) {
        StringRequest getActivitiesRequest = new StringRequest(TvShowsUrl.ACTIVITIES_GET + "/" + MyApplication.user.getId() + "?page=" + page.getNumber() + "&size=" + page.getSize(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONObject dataJsonObj = responseJson.getJSONObject("data");
                        JSONObject activityJsonObj = dataJsonObj.getJSONObject("activities");
                        page.setTotalPages(activityJsonObj.getInt("totalPages"));
                        page.setNumber(activityJsonObj.getInt("number"));
                        page.setLast(activityJsonObj.getBoolean("last"));
                        JSONArray contentJsonArr = activityJsonObj.getJSONArray("content");
                        for (int i = 0; i < contentJsonArr.length(); i++) {
                            JSONObject itemJson = contentJsonArr.getJSONObject(i);
                            TvShowActivity tvShowActivityItem = new TvShowActivity();
                            tvShowActivityItem.setId(itemJson.getInt("id"));
                            tvShowActivityItem.setContent(itemJson.getString("content"));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(itemJson.getLong("releaseTime"));
                            tvShowActivityItem.setReleaseTime(DateUtil.dateToStr(calendar.getTime()));
                            tvShowActivityItem.setPictureUrl(TvShowsUrl.ACTIVITY_IMAGE_URL + itemJson.getString("picUrl"));
                            datas.add(tvShowActivityItem);
                        }
                        handler.sendEmptyMessage(1);
                    } else {
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(getActivitiesRequest);
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridview);
        IVPortrait = (ImageView) view.findViewById(R.id.portrait_img);
        TVUsername = (TextView) view.findViewById(R.id.personal_fragment_username);
        TVFollowing = (TextView) view.findViewById(R.id.followingUser);
        TVFollowers = (TextView) view.findViewById(R.id.followers);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            startActivity(new Intent(getContext(), SendActivity.class));
        } else {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("detailActivity", datas.get(position - 1));
            startActivity(intent);
        }
    }
}
