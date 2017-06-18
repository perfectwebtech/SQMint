package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetail;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailListAdapter;
import com.example.shalhan4.sqmint.ui.main.MainActivity;
import com.example.shalhan4.sqmint.ui.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private ListView mListView;
    private UserDetailListAdapter mUserDetailListAdapter;
    private List<UserDetail> mUserDetailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setUp();
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
        this.mListView = (ListView) findViewById(R.id.lv_user_detail_list);
        this.mUserDetailList = new ArrayList<>();
        this.mUserDetailList.add(new UserDetail(1, "16/07/2018", "12:05:11"));
        this.mUserDetailList.add(new UserDetail(2, "12/06/2017", "11:05:11"));

        this.mUserDetailListAdapter = new UserDetailListAdapter(UserDetailActivity.this, this.mUserDetailList);
        this.mListView.setAdapter(mUserDetailListAdapter);
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


}
