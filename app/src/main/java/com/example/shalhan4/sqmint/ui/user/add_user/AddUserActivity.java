package com.example.shalhan4.sqmint.ui.user.add_user;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class AddUserActivity extends Activity implements AddUserView {
    private EditText mUsername;
    private TextView tvNotValid;
    private Button mSignUp;
    private AddUserPresenter mAddUserPresenter;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        this.mAddUserPresenter = new AddUserPresenter(this);
        mAddUserPresenter.setAddUserContext(this);
        setUp();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.setStatusOnline();

        this.mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                mAddUserPresenter.createAdmin(mUsername.getText().toString());
            }
        });
    }

    private void setUp()
    {
        this.mUsername = (EditText) findViewById(R.id.etUsername_addUser);
        this.mSignUp = (Button) findViewById(R.id.btnSignup);
        this.tvNotValid = (TextView) findViewById(R.id.tvNotValidAdmin);
    }


    @Override
    public void openAuthDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddUserActivity.this);
        View v = getLayoutInflater().inflate(R.layout.dialog_add_server, null);

//        EditText mPassword = (EditText) v.findViewById(R.id.et_password_add_user);
//        Button mBtnLogin = (Button) v.findViewById(R.id.btn_login_add_user);

        mBuilder.setView(v);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

//        mBtnLogin.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view)
//            {
//                Log.i("HALO => ", "HALO JUGA");
//            }
//        });
    }

    @Override
    public void createAdminNotValid()
    {
        this.tvNotValid.setVisibility(View.VISIBLE);
    }

    @Override
    public void createAdminValid()
    {
        finish();
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
