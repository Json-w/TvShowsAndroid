package com.example.jason.helloworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jason.helloworld.R;
import com.example.jason.helloworld.model.Comment;

import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private List<Comment> datas;

    public CommentsAdapter(List<Comment> datas) {
        this.datas = datas;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.commentUsernameTV = (TextView) convertView.findViewById(R.id.comment_user);
            viewHolder.commentContentTV = (TextView) convertView.findViewById(R.id.comment_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commentUsernameTV.setText(datas.get(position).getCommentUser().getUsername() + ":");
        viewHolder.commentContentTV.setText(datas.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        TextView commentUsernameTV, commentContentTV;
    }
}
