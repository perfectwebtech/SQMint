package com.example.shalhan4.sqmint.ui.user;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class UserPresenter {
    private UserView mUserView;

    public UserPresenter(UserView userView)
    {
        this.mUserView = userView;
    }

    public void getUserDetail(int id)
    {
        this.mUserView.openUserDetail(id);
    }
}
