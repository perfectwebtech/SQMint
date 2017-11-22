package com.example.shalhan4.sqmint.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
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

public class UserPresenter implements UserPresenterIntf {
    private UserView mUserView;
    SharedPreferences sharedPreferences;
    private Context context;
    //SharedPreferences
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";

    public UserPresenter(UserView userView)
    {
        this.mUserView = userView;
    }

    @Override
    public void getUserDetail(int id, String name)
    {
        this.mUserView.openUserDetail(id, name);
    }

    @Override
    public String getAccessToken() {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    @Override
    public void setUserContext(Context context) {
        this.context = context;
    }

    @Override
    public void startApi()
    {
        new SQMintApi().execute("http://192.168.43.13:53293/api/user/adminlist"); //laptop shalhan koneksi kosan
    }

    public class SQMintApi extends AsyncTask<String, String, List<User> > {

        protected List<User> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                Log.i("ACCESS TOKEN ", getAccessToken());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String result = stringBuilder.toString();
                    List<User> userList = new ArrayList<>();
                    JSONArray userArray = new JSONArray(result);
                    int length = userArray.length();
                    for(int i = 0; i < length; i++)
                    {
                        JSONObject userObject = userArray.getJSONObject(i);
                        User users = new User();
                        users.setId(userObject.getInt("id"));
                        users.setName(userObject.getString("name"));
                        users.setNip(userObject.getString("nip"));
                        users.setConnected(userObject.getBoolean("isConnected"));

                        Log.i("last login => ", userObject.getString("lastLogin"));
                        users.setLastLogin(userObject.getString("lastLogin"));
                        userList.add(users);
                    }


                    return userList;

                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(List<User> response) {
            super.onPostExecute(response);
            mUserView.setUserListAdapter(response);
        }
    }
}
