package com.example.shalhan4.sqmint.ui.user;

/**
 * Created by shalhan4 on 6/18/2017.
 */

public class User {
    int id;
    String name, lastLoginDate, lastLoginTime;

    public User(int id, String name, String lastLoginDate, String lastLoginTime) {
        this.id = id;
        this.name = name;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
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
}
