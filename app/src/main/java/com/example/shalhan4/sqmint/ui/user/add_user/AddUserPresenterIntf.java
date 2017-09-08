package com.example.shalhan4.sqmint.ui.user.add_user;

import android.content.Context;

/**
 * Created by shalhan4 on 6/21/2017.
 */

public interface AddUserPresenterIntf{
    public void createAdmin(String username);
    public void setAddUserContext(Context context);
    public String getAccessToken();
    public void isValid(String response);


}
