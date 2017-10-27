package com.example.shalhan4.sqmint.ui.job.job_detail;

import android.content.Intent;
import android.os.AsyncTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        this.jobDetailName = (TextView) findViewById(R.id.jobNameDetail);

        setUp();


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
}
