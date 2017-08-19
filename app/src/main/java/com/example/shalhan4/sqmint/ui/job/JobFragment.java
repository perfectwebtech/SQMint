package com.example.shalhan4.sqmint.ui.job;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment implements JobView {
    private ListView mListView;
    private JobListAdapter mJobAdapter;
    private JobPresenter mJobPresenter;

    public JobFragment() {
        this.mJobPresenter = new JobPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job, container, false);

        mJobPresenter.setJobContext(getActivity());
        mJobPresenter.startApi();

        this.mListView = (ListView) v.findViewById(R.id.job_list);

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Job mJobList = (Job) mJobAdapter.getItem(position);
                Log.i("JOB LIST BY ID ====> ", mJobList.getId() + "");


                mJobPresenter.getJobDetail(mJobList.getId());
            }
        });


        return v;
    }

    @Override
    public void openJobDetail(int id)
    {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
        intent.putExtra("JOB_ID", id);
        startActivity(intent);
    }

    @Override
    public void setJobListAdapter(List<Job> mJobList)
    {
        this.mJobAdapter = new JobListAdapter(getActivity(), mJobList);
        this.mListView.setAdapter(mJobAdapter);
    }

}
