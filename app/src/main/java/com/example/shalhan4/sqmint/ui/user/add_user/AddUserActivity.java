package com.example.shalhan4.sqmint.ui.user.add_user;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shalhan4.sqmint.R;

public class AddUserActivity extends Activity implements AddUserView {
    private EditText mUsername;
    private Button mSignUp;
    private AddUserPresenter mAddUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        this.mAddUserPresenter = new AddUserPresenter(this);

        setUp();

        this.mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                openAuthDialog();
            }
        });
    }

    private void setUp()
    {
        this.mUsername = (EditText) findViewById(R.id.etUsername_addUser);
        this.mSignUp = (Button) findViewById(R.id.btnSignup);
    }


    @Override
    public void openAuthDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddUserActivity.this);
        View v = getLayoutInflater().inflate(R.layout.dialog_auth, null);

        EditText mPassword = (EditText) v.findViewById(R.id.et_password_add_user);
        Button mBtnLogin = (Button) v.findViewById(R.id.btn_login_add_user);

        mBuilder.setView(v);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
