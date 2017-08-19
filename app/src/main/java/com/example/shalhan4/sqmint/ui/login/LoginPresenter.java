package com.example.shalhan4.sqmint.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by shalhan4 on 5/3/2017.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginActivity mLoginActivity;
    private static final String TAG = "login presenter";
    private String username;
    private String password;
    private int count = 1;
    private Context context;

    //shared preferences
    public static final String PREFERENCES_NAME = "SQMINT";
    public static final String USERNAME = "USERNAME";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";
    public static final String EXPIRES_IN = "EXPIRES_IN";
    public static final String IS_USER_LOGGEDIN = "IS_USER_LOGGEDIN";

    SharedPreferences sharedPreferences;

    public LoginPresenter(ILoginActivity loginActivity)
    {
        this.mLoginActivity = loginActivity;
    }

    @Override
    public void onServerLoginClick(Context context, String username, String password) {
        Log.i("USERNAME AND PASSWORD =", username + " AND " + password);
        this.context = context;
        if(username.equals("") || password.equals(""))
        {
            this.mLoginActivity.fieldIsNull(username.length(),password.length());
        }
        else
        {
            this.username = username;
            this.password = password;

//            new SQMintApi().execute("http://192.168.0.10:53293/token"); //laptop dikna koneksi kelly
//            new SQMintApi().execute("http://192.168.43.118:53293/token"); //laptop aten koneksi shalhan
//            new SQMintApi().execute("http://192.168.43.215:53293/token"); //laptop aten koneksi dikna
            new SQMintApi().execute("http://192.168.0.12:53293/token"); //laptop aten koneksi kelly


        }

//        this.mLoginActivity.loginIsValid();

    }

    public class SQMintApi extends AsyncTask<String, String, String > {
        protected void onPreExecute()
        {
            mLoginActivity.runProgressBar();
        }

        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                try
                {
                    String userAuth =
                            "username=" + URLEncoder.encode(username, "UTF-8") +
                                    "&password=" + URLEncoder.encode(password, "UTF-8") +
                                    "&grant_type=" + URLEncoder.encode("password", "UTF-8");

                    Log.i("USERNYA INI NIH", userAuth);

                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(userAuth);
                    Log.i("RESPONSE MESSAGE", urlConnection.getResponseMessage());
                    Log.i("RESPONSE CODE", urlConnection.getResponseCode() + "");
                    os.flush();
                    os.close ();

                    //Get Response

                    InputStream is = urlConnection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    Log.i("INI HASILNYA", response.toString());

                    return response.toString();


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
            mLoginActivity.stopProgressBar();
            mLoginActivity.enalbeComponent();
            Log.i("HASILNYA NIH", response);
            if(!response.isEmpty()) {
                Log.i("BERHASIL LOGIN", "YEAY");
                //get result
                try {
                    JSONObject userObj = new JSONObject(response);
                    Log.i("ACCES TOKENNYA NIH", userObj.getString("access_token"));

                    //shared preferences
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USERNAME, username);
                    editor.putString(ACCESS_TOKEN, userObj.getString("access_token"));
                    editor.putString(TOKEN_TYPE, userObj.getString("token_type"));
                    editor.putString(EXPIRES_IN, userObj.getString("expires_in"));
                    editor.putBoolean(IS_USER_LOGGEDIN, true);

                    editor.commit();
//                    //login
                    Log.i("USERNAME", sharedPreferences.getString(USERNAME, null));
                    mLoginActivity.loginIsValid();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else
            {
                Log.i("LOGIN GAGAL NIH", "YAHH GAGAL");
                mLoginActivity.loginNotValid();
            }


        }
    }
}
