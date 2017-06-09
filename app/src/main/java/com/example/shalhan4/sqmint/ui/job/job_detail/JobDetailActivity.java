package com.example.shalhan4.sqmint.ui.job.job_detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.Job;
import com.example.shalhan4.sqmint.ui.job.JobListAdapter;
import com.example.shalhan4.sqmint.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class JobDetailActivity extends AppCompatActivity implements JobDetailView{

    private Toolbar toolbar;
    private ListView mListView;
    private JobDetailListAdapter mJobDetailListAdapter;
    private List<JobDetail> mJobDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        setUp();
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
        this.mJobDetailList = new ArrayList<>();
        this.mJobDetailList.add(new JobDetail(1, "Select all from players", 20233221, "16/07/2018", "12:05:11", "26 Sec"));
        this.mJobDetailList.add(new JobDetail(2, "Delete where id = null", 202112322, "12/06/2017", "11:05:11", "11 Sec"));

        this.mJobDetailListAdapter = new JobDetailListAdapter(JobDetailActivity.this, this.mJobDetailList);
        this.mListView.setAdapter(mJobDetailListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(JobDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
