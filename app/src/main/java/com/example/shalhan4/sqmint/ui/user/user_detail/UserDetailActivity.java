package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetail;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailListAdapter;
import com.example.shalhan4.sqmint.ui.main.MainActivity;
import com.example.shalhan4.sqmint.ui.user.User;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setUp();

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
}
