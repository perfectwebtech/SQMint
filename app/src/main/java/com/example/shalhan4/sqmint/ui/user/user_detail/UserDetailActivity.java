package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetail;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailListAdapter;
import com.example.shalhan4.sqmint.ui.main.MainActivity;
import com.example.shalhan4.sqmint.ui.user.User;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements UserDetailView{
    private Toolbar toolbar;
    private ListView mListView;
    private TextView userDetailName;
    private UserDetailListAdapter mUserDetailListAdapter;
    private List<UserDetail> mUserDetailList;
    private int USER_ID;
    private String USER_NAME;
    private UserDetailPresenter mUserDetailPresenter;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setUp();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.setStatusOnline();
        this.USER_ID = getIntent().getIntExtra("USER_ID", 0);
        this.USER_NAME = getIntent().getStringExtra("USER_NAME");


        this.mUserDetailPresenter = new UserDetailPresenter(this, this.USER_ID);
        this.mUserDetailPresenter.setUserDetailContext(this);
        this.mUserDetailPresenter.startApi();
    }

    public void setUp()
    {
        //Toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_user_detail);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ListView
        this.userDetailName = (TextView) findViewById(R.id.tv_user_detail_name);
        this.mListView = (ListView) findViewById(R.id.lv_user_detail_list);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void setJobDetailListAdapter(List<UserDetail> mUserDetailList) {
        this.userDetailName.setText(this.USER_NAME);
        this.mUserDetailListAdapter = new UserDetailListAdapter(UserDetailActivity.this, mUserDetailList);
        this.mListView.setAdapter(this.mUserDetailListAdapter);
    }

    @Override
    public void userDetailListEmpty(){
        this.userDetailName.setText("History still empty");
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.setStatusOnline();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.setStatusOffline();
    }

    public String getAccessToken()
    {
        return this.sharedPreferences.getString("TOKEN_TYPE", null) + " " + this.sharedPreferences.getString("ACCESS_TOKEN", null);
//        Log.i("TOKEN TYPE", this.sharedPreferences.getString(ACCESS_TOKEN, null));

    }

    private void setStatusOnline()
    {
        this.online(this.sharedPreferences.getString("ADMIN_ID", ""));
    }

    private void setStatusOffline()
    {
        this.offline(this.sharedPreferences.getString("ADMIN_ID", ""));

    }

    private void offline(String id)
    {
        Log.i("ADMIN OFFLINE", id);
        new SQMintApiLogout().execute("http://192.168.43.13:53293/api/logout/" + id);
    }

    private void online(String id)
    {
        Log.i("ADMIN ONLINE", id);
        new SQMintApiOnline().execute("http://192.168.43.13:53293/api/login/" + id);

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
