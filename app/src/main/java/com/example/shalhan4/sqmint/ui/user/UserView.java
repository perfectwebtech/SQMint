package com.example.shalhan4.sqmint.ui.user;

import android.view.View;

import com.example.shalhan4.sqmint.ui.job.Job;

import java.util.List;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public interface UserView {
    public void openUserDetail(int id);
    public void openAddUser();
    public void setUserListAdapter(List<User> mUserList);

}
