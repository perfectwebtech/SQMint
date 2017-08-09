package com.example.shalhan4.sqmint.ui.job.job_detail;

import android.os.AsyncTask;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.job.Job;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalhan4 on 8/6/2017.
 */

public class JobDetailPresenter {
    private JobDetailView mJobDetailView;

    public JobDetailPresenter(JobDetailView mJobDetail, int jobId)
    {
        mJobDetailView = mJobDetail;
        new SQMintApi().execute("http://192.168.0.103:50447/API/jobs/" + jobId);
    }

    public class SQMintApi extends AsyncTask<String, String, List<JobDetail> > {

        protected List<JobDetail> doInBackground(String... params) {
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
                    List<JobDetail> jobDetailList = new ArrayList<>();
                    JSONArray jobDetailArray = new JSONArray(result);
                    int length = jobDetailArray.length();
                    for(int i = 0; i < length; i++)
                    {
                        JSONObject jobDetailObject = jobDetailArray.getJSONObject(i);
                        JobDetail jobDetails = new JobDetail();
                        jobDetails.setRunDateTime(jobDetailObject.getString("runDate"));
                        jobDetails.setStatus(jobDetailObject.getString("runOutcome"));
                        jobDetails.setDuration(jobDetailObject.getString("duration"));

                        String jobActivity = jobDetailObject.getString("jobActivity");
                        jobDetails.setJobName(jobDetailObject.getJSONObject("jobActivity").getString("name"));

//                        Log.i("JOB ACTIVITY", jobDetailObject.getJSONObject("jobActivity").length() + "");
//                        JSONArray jobArray = new JSONArray(jobActivity);
//                        for(int j = 0; j<jobArray.length(); j++)
//                        {
////                            JSONObject jobObject = jobArray.getJSONObject(j);
////                            jobDetails.setJobName(jobObject.getString("name"));
//                        }
////                        for(int j = 0; j< jobDetailObject.getJSONArray("jobActivity").length(); j++)
////                        {
////                            jobDetails.setJobName(jobDetailObject.getJSONArray("jobActivity").getJSONObject(j).getString("name"));
////                        }
                        jobDetailList.add(jobDetails);
                    }



                    return jobDetailList;

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

        protected void onPostExecute(List<JobDetail> response) {
            super.onPostExecute(response);
            mJobDetailView.setJobDetailListAdapter(response);
        }
    }

}
