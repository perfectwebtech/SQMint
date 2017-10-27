package com.example.shalhan4.sqmint.ui.usage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.job.Job;
import com.example.shalhan4.sqmint.ui.job.JobPresenter;

import org.json.JSONArray;
import org.json.JSONException;
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
    private Context context;

    SharedPreferences sharedPreferences;
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";


    public UsagePresenter(UsageView usageView)
    {
        this.mUsageView = usageView;
    }

    public void getUsage(int id)
    {
//        new SQMintApi().execute("http://192.168.0.103:50447/API/resources");
//
//        new SQMintApi().execute("http://192.168.0.10:53293/API/resource"); //laptop dikna koneksi kelly
//        new SQMintApi().execute("http://192.168.43.118:53293/API/resource"); //laptop aten koneksi shalhan
//        new SQMintApi().execute("http://192.168.43.215:53293/API/resource"); //laptop aten koneksi dikna
//        new SQMintApi().execute("http://192.168.0.12:53293/API/resource"); //laptop aten koneksi dikna
        new SQMintApi().execute("http://192.168.1.114:53293/api/resource/" + id); //laptop shalhan koneksi kosan



    }

    public void setUsageContext(Context context)
    {
        this.context = context;
    }

    public String getAccessToken()
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public class SQMintApi extends AsyncTask<String, String, List<Usage> > {

        protected List<Usage> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", getAccessToken());
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

                    return usageList;

                }
                catch (JSONException e)
                {
                    List<Usage> usageList = new ArrayList<>();
                    return usageList;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                List<Usage> usageList = new ArrayList<>();

                return usageList;
            }
        }

        protected void onPostExecute(List<Usage> response) {
            super.onPostExecute(response);
            if(!response.isEmpty())
                mUsageView.setResources(response);
            else if(response.isEmpty())
                mUsageView.connectionError();
        }
    }


    
}
