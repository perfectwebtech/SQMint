package com.example.shalhan4.sqmint.ui.job;


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

        new SQMintApi().execute("http://192.168.0.103:50447/API/jobs");



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

    public class SQMintApi extends AsyncTask<String, String, List<Job> > {

        protected List<Job> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String result = stringBuilder.toString();
                    List<Job> jobList = new ArrayList<>();
                    JSONArray jobArray = new JSONArray(result);
                    int length = jobArray.length();
                    for(int i = 0; i < length; i++)
                    {
                        JSONObject jobObject = jobArray.getJSONObject(i);
                        Job jobs = new Job();
                        jobs.setId(jobObject.getInt("id"));
                        jobs.setJobName(jobObject.getString("name"));
                        jobs.setLastRun(jobObject.getString("lastRun"));
                        jobs.setStatus(jobObject.getString("lastRunOutcome"));
                        jobList.add(jobs);
                    }


                    return jobList;

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

        protected void onPostExecute(List<Job> response) {
            super.onPostExecute(response);

            mJobList = response;
            mJobAdapter = new JobListAdapter(getActivity(), mJobList);
            mListView.setAdapter(mJobAdapter);

        }
    }
}
