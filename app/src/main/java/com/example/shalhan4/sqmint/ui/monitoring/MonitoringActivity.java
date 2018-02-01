package com.example.shalhan4.sqmint.ui.monitoring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.JobFragment;
import com.example.shalhan4.sqmint.ui.usage.UsageFragment;
import com.example.shalhan4.sqmint.ui.user.UserFragment;

import java.net.HttpURLConnection;
import java.net.URL;

public class MonitoringActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int SERVER_ID;
    private String IP_ADDRESS;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        this.setStatusOnline();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        this.SERVER_ID = extras.getInt("SERVER_ID", 0);
        this.IP_ADDRESS = extras.getString("IP_ADDRESS");

        Log.i("MONITORING ACTIVITY =>", this.SERVER_ID + " " + this.IP_ADDRESS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.IP_ADDRESS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.setStatusOnline();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.setStatusOffline();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    JobFragment jobFragment = new JobFragment();
                    jobFragment.setServerId(SERVER_ID);
                    return jobFragment;
                case 1:
                    UsageFragment usageFragment = new UsageFragment();
                    usageFragment.setServerId(SERVER_ID);
                    return usageFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Job";
                case 1:
                    return "Usage";
            }
            return null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getAccessToken()
    {
        return this.sharedPreferences.getString("TOKEN_TYPE", null) + " " + this.sharedPreferences.getString("ACCESS_TOKEN", null);
//        Log.i("TOKEN TYPE", this.sharedPreferences.getString(ACCESS_TOKEN, null));

    }

    private void setStatusOnline()
    {
        this.online(this.sharedPreferences.getString("ADMIN_ID", ""));
    }

    private void setStatusOffline()
    {
        this.offline(this.sharedPreferences.getString("ADMIN_ID", ""));

    }

    private void offline(String id)
    {
        Log.i("ADMIN OFFLINE", id);
        new SQMintApiLogout().execute("http://192.168.43.13:53293/api/logout/" + id);
    }

    private void online(String id)
    {
        Log.i("ADMIN ONLINE", id);
        new SQMintApiOnline().execute("http://192.168.43.13:53293/api/login/" + id);

    }

    public class SQMintApiLogout extends AsyncTask<String, String, String > {

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setDoInput(true);
                try
                {
                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCESS";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("LOGOUT HASIL", response + urlConnection.getResponseCode());

                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "FAILED";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
        }
    }

    public class SQMintApiOnline extends AsyncTask<String, String, String > {

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Authorization", getAccessToken());
                urlConnection.setDoInput(true);
                try
                {
                    int code = 204;
                    String response;
                    if(urlConnection.getResponseCode() == 204)
                    {
                        response = "SUCCESS";
                    }
                    else
                    {
                        response = "FAILED";
                    }

                    Log.i("LOGIN HASIL", response + urlConnection.getResponseCode());

                    return response;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "FAILED";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response.equals("SUCCESS"))
                Log.i("SET ONLINE SUCCESS", "YEAY");
        }
    }
}
