package com.example.jason.helloworld.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.adapters.FollowersAdapter;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.Follower;
import com.example.jason.helloworld.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {

    private List<Follower> data = new ArrayList<>();
    private RequestQueue requestQueue;
    private ListView listView;
    private FollowersAdapter followersAdapter;
    private Button backBtn;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            followersAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        listView = (ListView) this.findViewById(R.id.follower_list_view);
        backBtn = (Button) this.findViewById(R.id.follower_back_button);
        requestQueue = Volley.newRequestQueue(this);
        fetchFollowing();
        followersAdapter = new FollowersAdapter(data, requestQueue);
        listView.setAdapter(followersAdapter);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchFollowing() {
        StringRequest fetchDataRequest = new StringRequest(TvShowsUrl.FIND_FOLLOWER + "/" + MyApplication.user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 1) {
                        JSONArray dataJson = responseJson.getJSONArray("data");
                        for (int i = 0; i < dataJson.length(); i++) {
                            JSONObject itemJson = dataJson.getJSONObject(i);
                            Follower follower = new Follower();
                            follower.setId(itemJson.getInt("id"));
                            User user = new User();
                            JSONObject userJson = itemJson.getJSONObject("follower");
                            user.setId(userJson.getInt("id"));
                            user.setUsername(userJson.getString("username"));
                            user.setEmail(userJson.getString("email"));
                            String prePicUrl = userJson.getString("portraitUrl");
                            user.setPortraitUrl(TvShowsUrl.PIC_URL + prePicUrl.substring(prePicUrl.lastIndexOf("/") + 1));
                            follower.setFollower(user);
                            follower.setInterFollow(itemJson.getBoolean("interFollow"));
                            data.add(follower);
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
                Toast.makeText(FollowersActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                myHandler.sendEmptyMessage(0);
            }
        });
        requestQueue.add(fetchDataRequest);
    }

}
