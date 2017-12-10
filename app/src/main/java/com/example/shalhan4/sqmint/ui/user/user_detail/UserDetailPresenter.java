package com.example.shalhan4.sqmint.ui.user.user_detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetail;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailPresenter;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailView;
import com.example.shalhan4.sqmint.ui.user.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalhan4 on 9/9/2017.
 */

public class UserDetailPresenter implements UserDetailPresenterIntf {
    private UserDetailView mUserDetailView;
    private int userId;
    private Context context;
    SharedPreferences sharedPreferences;

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";

    public UserDetailPresenter(UserDetailView mUserDetail, int userId){
        mUserDetailView = mUserDetail;
        this.userId = userId;
        Log.i("USER ID ", userId + "");
    }


    @Override
    public void startApi() {
        new SQMintApi().execute("http://192.168.43.13:53293/api/admin/" + this.userId); //laptop shalhan koneksi kosan
    }

    @Override
    public void setUserDetailContext(Context context) {
        this.context = context;
    }

    public String getAccessToken()
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return this.sharedPreferences.getString(TOKEN_TYPE, null) + " " + this.sharedPreferences.getString(ACCESS_TOKEN, null);

    }

    public class SQMintApi extends AsyncTask<String, String, List<UserDetail> > {

        protected List<UserDetail> doInBackground(String... params) {
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
                    Log.i("USER DETAIL RESULT ", result);
                    List<UserDetail> userDetailList = new ArrayList<>();
                    JSONArray userDetailArray = new JSONArray(result);
                    int length = userDetailArray.length();
                    for(int i = 0; i < length; i++)
                    {
                        JSONObject userDetailObject = userDetailArray.getJSONObject(i);
                        UserDetail userDetails = new UserDetail();
                        userDetails.setUserId(userDetailObject.getInt("adminAccountId"));
                        userDetails.setLoginDateTime(userDetailObject.getString("loginDate"));
                        Log.i("Login date time ", userDetailObject.getString("loginDate"));
//                        userDetails.setName(userDetailObject.getJSONObject("jobList").getString("name"));

                        userDetailList.add(userDetails);
                    }

                    Log.i("USER DETAIL ", userDetailList.toString());

                    return userDetailList;

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

        protected void onPostExecute(List<UserDetail> response) {
            super.onPostExecute(response);
            if(!response.isEmpty())
                mUserDetailView.setJobDetailListAdapter(response);
            else
                mUserDetailView.userDetailListEmpty();
        }
    }



}
