package com.example.shalhan4.sqmint.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.usage.UsagePresenter;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by shalhan4 on 6/5/2017.
 */

public class MainPresenter implements IMainPresenter{
    private IMainActivity mMainActivity;
    private Context context;
    SharedPreferences sharedPreferences;
    //SharedPreferences
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";


    public MainPresenter(IMainActivity mainActivity)
    {
        this.mMainActivity = mainActivity;
    }

    public void logout(String id)
    {
        Log.i("ADMIN LOGOUT", id);
        new SQMintApi().execute("http://192.168.43.13:53293/api/logout/" + id);
    }

    public void offline(String id)
    {
        Log.i("ADMIN OFFLINE", id);
        new SQMintApiLogout().execute("http://192.168.43.13:53293/api/logout/" + id);
    }

    public void online(String id)
    {
        Log.i("ADMIN ONLINE", id);
        new SQMintApiOnline().execute("http://192.168.43.13:53293/api/login/" + id);

    }

    public void setServerContext(Context context) {
        this.context = context;
    }


    public String getAccessToken() {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public class SQMintApi extends AsyncTask<String, String, String > {

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                urlConnection.setDoInput(true);
                try
                {
                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCESS";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("LOGOUT HASIL", response + urlConnection.getResponseCode());

                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "FAILED";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response.equals("SUCCESS"))
                mMainActivity.logoutRedirect();
        }
    }

    public class SQMintApiLogout extends AsyncTask<String, String, String > {

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                urlConnection.setDoInput(true);
                try
                {
                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCESS";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("LOGOUT HASIL", response + urlConnection.getResponseCode());

                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "FAILED";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
        }
    }

    public class SQMintApiOnline extends AsyncTask<String, String, String > {

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                urlConnection.setDoInput(true);
                try
                {
                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCESS";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("LOGIN HASIL", response + urlConnection.getResponseCode());

                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "FAILED";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response.equals("SUCCESS"))
                Log.i("SET ONLINE SUCCESS", "YEAY");
        }
    }


}
