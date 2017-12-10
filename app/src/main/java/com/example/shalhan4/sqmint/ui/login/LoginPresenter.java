package com.example.shalhan4.sqmint.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;

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
    private String token;

    //shared preferences
    public static final String USERNAME = "USERNAME";
    public static final String ADMIN_ID = "ADMIN_ID";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";
    public static final String EXPIRES_IN = "EXPIRES_IN";
    public static final String IS_USER_LOGGEDIN = "IS_USER_LOGGEDIN";
    public static final String NAME = "NAME";
    public static final String STATUS = "STATUS";


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

            new SQMintApi().execute("http://192.168.43.13:53293/request_token"); //koneksi kosan


        }

//        this.mLoginActivity.loginIsValid();

    }

    public void updateFirebaseToken(String admin_id, String token)
    {
        this.token = token;
        Log.i("ID ADMIN ", admin_id);
        new FirebaseToken().execute("http://192.168.43.13:53293/api/admin/"+ admin_id);
    }

    public String getAccessToken()
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);
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
                urlConnection.setReadTimeout(25000);
                urlConnection.setConnectTimeout(25000);
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
                    Log.i("START OUTPUT STREAM", "HARUSNYA");

                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(userAuth);
//                    Log.i("HARUSNYA BERHASIL", urlConnection.getResponseMessage());
                    Log.i("HARUSNYA 200", urlConnection.getResponseCode() + "");
                    os.flush();
                    os.close ();
                    if(urlConnection.getResponseCode() == 400)
                    {
                        String response = "";
                        return response;
                    }
                    //Get Response
                    Log.i("END OUTPUT STREAM", "HARUSNYA");


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
            mLoginActivity.enableComponent();
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
                    editor.putString(NAME, userObj.getString("name"));
                    editor.putString(STATUS, userObj.getString("status"));
                    editor.putString(ADMIN_ID, userObj.getString("admin_id"));
                    editor.putString(ACCESS_TOKEN, userObj.getString("access_token"));
                    editor.putString(TOKEN_TYPE, userObj.getString("token_type"));
                    editor.putString(EXPIRES_IN, userObj.getString("expires_in"));
                    editor.putBoolean(IS_USER_LOGGEDIN, true);

                    editor.commit();
                    //firebase token
                    String firebaseToken = FirebaseInstanceId.getInstance().getToken();
                    Log.i("FIREBASE TOKEN ", firebaseToken);
                    mLoginActivity.saveFirebaseToken(sharedPreferences.getString(ADMIN_ID, null), firebaseToken);
//                    //login
                    Log.i("USERNAME", sharedPreferences.getString(USERNAME, null));
                    Log.i("STATUS", sharedPreferences.getString(STATUS, null));

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

    public class FirebaseToken extends AsyncTask<String, String, String > {
        protected void onPreExecute()
        {

        }

        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setDoInput(true);
                try
                {
                    String userAuth = "token=" + URLEncoder.encode(token, "UTF-8");
                    Log.i("SIMPEN FIREBASE TOKEN", token);


                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(userAuth);
//                    Log.i("HARUSNYA BERHASIL", urlConnection.getResponseMessage());
                    Log.i("HARUSNYA 204", urlConnection.getResponseCode() + "");
                    os.flush();
                    os.close ();
                    String response;
                    if(urlConnection.getResponseCode() == 400)
                    {
                        Log.i("UPDATE FIREBAS GAGAL", urlConnection.getResponseCode() + "");
                        response = "FAILED";
                    }
                    else
                    {
                        Log.i("UPDATE FIREBAS BERHASIL", urlConnection.getResponseCode() + "");
                        response = "SUCCESS";
                    }

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
        }
    }
}
