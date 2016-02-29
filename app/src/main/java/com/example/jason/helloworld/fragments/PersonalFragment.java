package com.example.jason.helloworld.fragments;

import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.adapters.PersonalGridViewAdapter;
import com.example.jason.helloworld.model.TvShowActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private ImageView IVPortrait;
    private TextView TVUsername, TVFollowing, TVFollowers;
    private PersonalGridViewAdapter mAdapter;
    private List<TvShowActivity> datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_fragment, container, false);
        initView(view);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        datas = new ArrayList<>();
        mAdapter = new PersonalGridViewAdapter(datas, requestQueue);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridview);
        IVPortrait = (ImageView) view.findViewById(R.id.portrait_img);
        TVUsername = (TextView) view.findViewById(R.id.personal_fragment_username);
        TVFollowing = (TextView) view.findViewById(R.id.following);
        TVFollowers = (TextView) view.findViewById(R.id.followers);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "你点击了" + position, Toast.LENGTH_SHORT).show();
    }
}
