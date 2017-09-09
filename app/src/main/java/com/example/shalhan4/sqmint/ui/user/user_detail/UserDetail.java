package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.util.Log;

import com.example.shalhan4.sqmint.ui.user.User;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class UserDetail {
    private int id, userId;
    private String loginDate, loginTime, name;

    public UserDetail(){

    }

    public UserDetail(int id, String loginDate, String loginTime) {
        this.id = id;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setLoginDateTime(String loginDateTime) {
        Log.i("LOGIN DATE TIME ", loginDateTime);
        if(loginDateTime.equals("null"))
        {
            setLoginTime("-");
            setLoginDate("-");
        }
        else {
            String[] splitted = loginDateTime.split("T");
            Log.i("USER LAST LOGIN ", splitted[0].toString());
            setLoginDate(splitted[0]);
            setLoginTime(splitted[1]);
        }
    }

}
