package com.example.shalhan4.sqmint.ui.job;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalhan4 on 6/8/2017.
 */

public class JobPresenter implements JobPresenterIntf{
    private JobView mJobView;
    SharedPreferences sharedPreferences;
    private Context context;
    //SharedPreferences
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";



    public JobPresenter(JobView jobView)
    {
        this.mJobView = jobView;
    }

    @Override
    public void startApi()
    {
        new SQMintApi().execute("http://192.168.0.27:53293/api/job/getjob"); //laptop shalhan koneksi kosan
    }

    @Override
    public String getAccessToken()
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);
//        Log.i("TOKEN TYPE", this.sharedPreferences.getString(ACCESS_TOKEN, null));

    }

    @Override
    public void setJobContext(Context context)
    {
        this.context = context;
    }


    @Override
    public void getJobDetail(int id) {
        this.mJobView.openJobDetail(id);
    }

    public class SQMintApi extends AsyncTask<String, String, List<Job> > {

        protected List<Job> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                Log.i("ACCESS TOKEN ", getAccessToken());
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
                        jobs.setJobId(jobObject.getString("jobId"));
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
            mJobView.setJobListAdapter(response);
        }
    }

}
