package com.example.shalhan4.sqmint.ui.user.add_user;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.main.MainActivity;

import org.w3c.dom.Text;

public class AddUserActivity extends Activity implements AddUserView {
    private EditText mUsername;
    private TextView tvNotValid;
    private Button mSignUp;
    private AddUserPresenter mAddUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        this.mAddUserPresenter = new AddUserPresenter(this);
        mAddUserPresenter.setAddUserContext(this);
        setUp();

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
        View v = getLayoutInflater().inflate(R.layout.dialog_auth, null);

        EditText mPassword = (EditText) v.findViewById(R.id.et_password_add_user);
        Button mBtnLogin = (Button) v.findViewById(R.id.btn_login_add_user);

        mBuilder.setView(v);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Log.i("HALO => ", "HALO JUGA");
            }
        });
    }

    @Override
    public void createAdminNotValid()
    {
        this.tvNotValid.setVisibility(View.VISIBLE);
    }

    @Override
    public void createAdminValid()
    {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}
