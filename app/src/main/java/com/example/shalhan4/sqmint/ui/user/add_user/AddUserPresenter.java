package com.example.shalhan4.sqmint.ui.user.add_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by shalhan4 on 6/21/2017.
 */

public class AddUserPresenter implements AddUserPresenterIntf {
    private AddUserView mAddUser;
    private String username;
    SharedPreferences sharedPreferences;
    Context context;
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";


    public AddUserPresenter(AddUserView addUser)
    {
        this.mAddUser = addUser;
    }

    @Override
    public void createAdmin(String username)
    {
        this.username = username;
        Log.i("USERNAMENYA ", username);
        if(this.username.equals(""))
            mAddUser.createAdminNotValid();
        else
            new SQMintApi().execute("http://192.168.1.114:53293/api/admin/addadmin");
    }

    @Override
    public void setAddUserContext(Context context)
    {
        this.context = context;
    }

    @Override
    public String getAccessToken()
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);

    }

    @Override
    public void isValid(String response) {
        if(response.equals("SUCCEDD"))
        {
            mAddUser.createAdminValid();
        }
        else
        {
            mAddUser.createAdminNotValid();
        }
    }

    public class SQMintApi extends AsyncTask<String, String, String > {
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(20000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                Log.i("HARUSNYA ACCESS TOKEN ", getAccessToken());
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                try
                {
                    String userAuth = "username=" + URLEncoder.encode(username, "UTF-8");
                    Log.i("START OUTPUT STREAM", "HARUSNYA");

                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(userAuth);
                    Log.i("HARUSNYA 204", urlConnection.getResponseCode() + "");
                    os.flush();
                    os.close ();

                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCEDD";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("INI HASILNYA", response);

                    return response;


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

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.i("RESPONSE ADMIN ", response);
            isValid(response);
        }
    }
}
