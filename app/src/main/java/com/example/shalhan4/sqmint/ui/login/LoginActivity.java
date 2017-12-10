package com.example.shalhan4.sqmint.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.main.MainActivity;

/**
 * Created by shalhan4 on 4/27/2017.
 */

public class LoginActivity extends AppCompatActivity implements ILoginActivity, View.OnClickListener {

    LoginPresenter mPresenter;
    EditText etUsername;
    EditText etPassword;
    Button bLogin;
    TextView tvEmptyField;
    ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(this.sharedPreferences.getBoolean("IS_USER_LOGGEDIN", false))
        {
            Intent intent = new Intent(this , MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);

        this.mPresenter = new LoginPresenter(this);
        this.etUsername = (EditText) findViewById(R.id.etUsername);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        this.bLogin = (Button) findViewById(R.id.btnLogin);
        this.tvEmptyField = (TextView) findViewById(R.id.tvEmptyField);
        this.mProgress = (ProgressBar) findViewById(R.id.progressBar);


        this.bLogin.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        mPresenter.onServerLoginClick(LoginActivity.this, this.etUsername.getText().toString(), this.etPassword.getText().toString());
    }

    @Override
    public void onResume(){
        super.onResume();
        if(this.sharedPreferences.getBoolean("IS_USER_LOGGEDIN", false))
        {
            this.finish();
            System.exit(0);
        }
    }


    @Override
    public void loginIsValid() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginNotValid(){
        this.tvEmptyField.setText("Your username or password is not valid");
        this.tvEmptyField.setVisibility(View.VISIBLE);

        this.etUsername.getBackground().mutate().setColorFilter(getResources().getColor(R.color.fieldOnError), PorterDuff.Mode.SRC_ATOP);
        this.etPassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.fieldOnError), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void fieldIsNull(int fieldUsername, int fieldPassword){
        this.tvEmptyField.setVisibility(View.VISIBLE);

        if(fieldUsername == 0)
            this.etUsername.getBackground().mutate().setColorFilter(getResources().getColor(R.color.fieldOnError), PorterDuff.Mode.SRC_ATOP);
        if(fieldPassword == 0)
            this.etPassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.fieldOnError), PorterDuff.Mode.SRC_ATOP);
    }

    private Thread thread;

    @Override
    public void runProgressBar()
    {
        this.mProgress.setVisibility(View.VISIBLE);
        // Start lengthy operation in a background thread
        thread = new Thread(new Runnable() {
            public void run() {
                while ( mProgressStatus < 100) {
                    mProgressStatus += 1;

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        });

        thread.start();

    }

    @Override
    public void stopProgressBar()
    {
        this.mProgress.setVisibility(View.INVISIBLE);
        thread.interrupt();
    }
    @Override
    public void disableComponent()
    {
        this.etUsername.setFocusable(false);
        this.etPassword.setFocusable(false);
        this.bLogin.setFocusable(false);
    }

    @Override
    public void enableComponent()
    {
        this.etUsername.setEnabled(true);
        this.etPassword.setEnabled(true);
        this.bLogin.setEnabled(true);
    }

    @Override
    public void saveFirebaseToken(String adminId, String token) {
        this.mPresenter.updateFirebaseToken(adminId, token);
    }
}
