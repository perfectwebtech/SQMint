package com.example.shalhan4.sqmint.ui.job.job_detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.Job;
import com.example.shalhan4.sqmint.ui.job.JobFragment;
import com.example.shalhan4.sqmint.ui.job.JobListAdapter;
import com.example.shalhan4.sqmint.ui.job.JobPresenter;
import com.example.shalhan4.sqmint.ui.main.MainActivity;
import com.example.shalhan4.sqmint.ui.monitoring.MonitoringActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JobDetailActivity extends AppCompatActivity implements JobDetailView{

    private Toolbar toolbar;
    private ListView mListView;
    private JobDetailListAdapter mJobDetailListAdapter;
    private int JOB_ID;
    private int SERVER_ID;
    private JobDetailPresenter mJobDetailPresenter;
    private TextView jobDetailName;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        this.jobDetailName = (TextView) findViewById(R.id.jobNameDetail);


        setUp();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.setStatusOnline();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        this.SERVER_ID = extras.getInt("SERVER_ID", 0);
        this.JOB_ID = extras.getInt("JOB_ID",0);

        this.mJobDetailPresenter = new JobDetailPresenter(this, this.JOB_ID, this.SERVER_ID);
        this.mJobDetailPresenter.setJobDetailContext(this);
        this.mJobDetailPresenter.startApi();
    }

    @Override
    public void setUp()
    {
        //Toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_job_detail);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ListView
        this.mListView = (ListView) findViewById(R.id.lv_job_detail_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setJobDetailListAdapter(List<JobDetail> mJobDetailList)
    {
        this.jobDetailName.setText(mJobDetailList.get(0).getJobName());
        this.mJobDetailListAdapter = new JobDetailListAdapter(JobDetailActivity.this, mJobDetailList);
        this.mListView.setAdapter(this.mJobDetailListAdapter);
    }

    @Override
    public void jobDetailListEmpty(){
        this.jobDetailName.setText("History still empty");
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
