package com.example.shalhan4.sqmint.ui.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;

import java.util.List;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUserList;

    public UserListAdapter(Context mContext, List<User> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @Override
    public int getCount() {
        return this.mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.user_list, null);

        TextView tvNip = (TextView) v.findViewById(R.id.user_nip);
        TextView tvUserName = (TextView) v.findViewById(R.id.user_name);
        TextView tvLastLoginDate = (TextView) v.findViewById(R.id.tv_server_username);
        TextView tvLastLoginTime = (TextView) v.findViewById(R.id.user_last_login_time);
        ImageView ivConnectStatus = (ImageView) v.findViewById(R.id.iv_connect_status);


        tvNip.setText(this.mUserList.get(position).getNip());
        tvUserName.setText(this.mUserList.get(position).getName());
        tvLastLoginDate.setText(this.mUserList.get(position).getLastLoginDate());
        tvLastLoginTime.setText(this.mUserList.get(position).getLastLoginTime());


        v.setTag(mUserList.get(position).getId());
        return v;
    }
}
