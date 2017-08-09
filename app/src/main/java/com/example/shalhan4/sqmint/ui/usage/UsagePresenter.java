package com.example.shalhan4.sqmint.ui.usage;

import android.os.AsyncTask;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.job.Job;
import com.example.shalhan4.sqmint.ui.job.JobPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalhan4 on 8/9/2017.
 */

public class UsagePresenter {
    private UsageView mUsageView;

    public UsagePresenter(UsageView usageView)
    {
        this.mUsageView = usageView;
    }

    public void getUsage()
    {
        new SQMintApi().execute("http://192.168.0.103:50447/API/resources");

    }

    public class SQMintApi extends AsyncTask<String, String, List<Usage> > {

        protected List<Usage> doInBackground(String... params) {
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
                    Log.i("RESOURCES ===> ", result);

                    List<Usage> usageList = new ArrayList<>();
                    JSONObject usageObject = new JSONObject(result);
                    Usage usages = new Usage();
                    usages.setId(usageObject.getInt("id"));
                    usages.setAvailableMemory(usageObject.getDouble("availableMemory"));
                    usages.setProcessorUsage(usageObject.getDouble("processorUsage"));
                    usageList.add(usages);
//                    JSONArray usageArray = new JSONArray(result);
//                    int length = usageArray.length();
//                    Log.i("RESULT ===> ", usageArray.length() + "  " );
//                    for(int i = 0; i < length; i++)
//                    {
//                        JSONObject usageObject = usageArray.getJSONObject(i);
//                        Usage usages = new Usage();
//                        usages.setId(usageObject.getInt("id"));
//                        usages.setAvailableMemory(usageObject.getDouble("availableMemory"));
//                        usages.setProcessorUsage(usageObject.getDouble("processorUsage"));
//                        usageList.add(usages);
//                    }



                    return usageList;

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

        protected void onPostExecute(List<Usage> response) {
            super.onPostExecute(response);
            mUsageView.setMemoryUsage(response);
        }
    }


    
}
