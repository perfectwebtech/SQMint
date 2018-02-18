package com.example.shalhan4.sqmint.ui.job;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<Job> jobList;
    private JobListAdapter mJobAdapter;
    private JobPresenter mJobPresenter;
    private int SERVER_ID;
    private View view;
    private EditText mSearch;


    public JobFragment() {
        this.mJobPresenter = new JobPresenter(this);
    }

    public void setServerId(int id){this.SERVER_ID = id;}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job, container, false);
        this.view = v;
        mJobPresenter.setJobContext(getActivity());
        mJobPresenter.startApi(this.SERVER_ID);


        this.mListView = (ListView) v.findViewById(R.id.job_list);

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Job mJobList = (Job) mJobAdapter.getItem(position);
                Log.i("JOB LIST BY ID ====> ", mJobList.getId() + "");


                mJobPresenter.getJobDetail(mJobList.getId(), SERVER_ID);
            }
        });

        this.mSearch = (EditText) v.findViewById(R.id.et_search_job);
        this.mSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(""))
                {
                    setJobListAdapter(jobList);
                    Log.i("EMPTY STRING ", "YEAY");
                    return;
                }
                int size = jobList.size();
                List<Job> searchResult = new ArrayList<Job>();
                for(int i = 0; i< size; i++)
                {
                    if(jobList.get(i).getJobName().toLowerCase().contains(s))
                    {
                        searchResult.add(jobList.get(i));
                        Log.i("SEARCH RESULT ", jobList.get(i).getJobName() + " " + jobList.get(i).getId());

                    }
                }

                mJobAdapter = new JobListAdapter(getActivity(), searchResult);
                mListView.setAdapter(mJobAdapter);

            }
        });

        return v;
    }

    @Override
    public void openJobDetail(int id, int serverId)
    {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("JOB_ID", id);
        extras.putInt("SERVER_ID", serverId);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void setJobListAdapter(List<Job> mJobList)
    {
        this.jobList = mJobList;
        this.mJobAdapter = new JobListAdapter(getActivity(), mJobList);
        this.mListView.setAdapter(mJobAdapter);
    }

    @Override
    public void connectionError() {
        LinearLayout errorMonitoring = (LinearLayout) getActivity().findViewById(R.id.bg_error_monitoring);
        errorMonitoring.setVisibility(this.view.VISIBLE);

    }

}
