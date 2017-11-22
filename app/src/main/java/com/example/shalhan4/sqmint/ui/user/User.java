package com.example.shalhan4.sqmint.ui.user;

import android.util.Log;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class User {
    boolean isConnected;
    int id;
    String name, lastLoginDate, lastLoginTime, nip;

    public User(int id, String name, String lastLoginDate, String lastLoginTime) {
        this.id = id;
        this.name = name;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
    }

    public User()
    {

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setLastLogin(String lastLogin) {
        if(lastLogin.equals("null"))
        {
            setLastLoginTime("-");
            setLastLoginDate("-");
        }
        else {
            String[] splitted = lastLogin.split("T");
            Log.i("LAST LOGIN ", splitted.toString());
            setLastLoginDate(splitted[0]);
            setLastLoginTime(splitted[1]);
        }
    }
}
