package com.example.shalhan4.sqmint.ui.job;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment implements JobView {
    private ListView mListView;
    private JobListAdapter mJobAdapter;
    private List<Job> mJobList;
    private JobPresenter mJobPresenter;

    public JobFragment() {
        this.mJobPresenter = new JobPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job, container, false);

        this.mListView = (ListView) v.findViewById(R.id.job_list);

        this.mJobList = new ArrayList<>();
        this.mJobList.add(new Job(1, "Select all from players", "16/07/2018", "12:05:11"));
        this.mJobList.add(new Job(2, "Delete where id = null", "12/06/2017", "11:05:11"));

        this.mJobAdapter = new JobListAdapter(getActivity(), mJobList);
        this.mListView.setAdapter(mJobAdapter);

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mJobPresenter.getJobDetail(position);
            }
        });

        return v;
    }

    @Override
    public void openJobDetail(int id)
    {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
        startActivity(intent);
    }
}
