package com.example.shalhan4.sqmint.ui.user.user_detail;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class UserDetail {
    private int id;
    private String loginDate, loginTime;

    public UserDetail(int id, String loginDate, String loginTime) {
        this.id = id;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
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
}
