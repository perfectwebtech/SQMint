package com.example.shalhan4.sqmint.ui.user;

import android.os.AsyncTask;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.usage.Usage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
