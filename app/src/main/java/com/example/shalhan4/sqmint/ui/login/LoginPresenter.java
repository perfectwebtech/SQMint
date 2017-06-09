package com.example.shalhan4.sqmint.ui.login;

import android.content.Context;

/**
 * Created by shalhan4 on 5/3/2017.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginActivity mLoginActivity;
    private static final String TAG = "login presenter";

    public LoginPresenter(ILoginActivity loginActivity)
    {
        this.mLoginActivity = loginActivity;
    }

    @Override
    public void onServerLoginClick(Context context, String username, String password) {

        if(username.trim().length() == 0 || password.trim().length() == 0)
        {
            this.mLoginActivity.fieldIsNull(username.trim().length(), password.trim().length());
        }
        else
        {
            if(username.equals("shalhan") && password.equals("123456"))
                this.mLoginActivity.loginIsValid();
            else
                this.mLoginActivity.loginNotValid();
        }

    }
}
