package com.example.jason.helloworld.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.helloworld.R;

public class PersonalFragment extends Fragment {
    private GridView gridView;
    private ImageView IVPortrait;
    private TextView TVUsername, TVFollowing, TVFollowers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridview);
        IVPortrait = (ImageView) view.findViewById(R.id.portrait_img);
        TVUsername = (TextView) view.findViewById(R.id.personal_fragment_username);
        TVFollowing = (TextView) view.findViewById(R.id.following);
        TVFollowers = (TextView) view.findViewById(R.id.followers);
    }
}
