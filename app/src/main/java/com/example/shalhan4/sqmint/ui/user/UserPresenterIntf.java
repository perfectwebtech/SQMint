package com.example.shalhan4.sqmint.ui.user;

import android.content.Context;

/**
 * Created by shalhan4 on 9/7/2017.
 */

public interface UserPresenterIntf {
    public void getUserDetail(int id, String name);
    public String getAccessToken();
    public void setUserContext(Context context);
    public void startApi();
}
