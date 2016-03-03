package com.example.jason.helloworld.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.jason.helloworld.R;
import com.example.jason.helloworld.widget.XListView;

public class SquareFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView xListView;

    public SquareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.square_fragment, container, false);
        xListView = (XListView) view.findViewById(R.id.square_fragment_activity_list);
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
}
