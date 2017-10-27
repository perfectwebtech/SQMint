package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.user.User;

import java.util.List;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class UserDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserDetail> mUserList;

    public UserDetailListAdapter(Context mContext, List<UserDetail> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.user_detail_list, null);

        TextView tvLoginDate = (TextView) v.findViewById(R.id.tv_login_date);
        TextView tvLoginTime = (TextView) v.findViewById(R.id.tv_login_time);

        tvLoginDate.setText(this.mUserList.get(position).getLoginDate());
        tvLoginTime.setText(this.mUserList.get(position).getLoginTime());

        v.setTag(mUserList.get(position).getId());

        return v;
    }
}
