package com.example.shalhan4.sqmint.ui.server;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.main.MainActivity;
import com.example.shalhan4.sqmint.ui.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment {
    private FloatingActionButton mFab;
    private ListView mListView;



    public ServerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_server, container, false);

        this.mFab = (FloatingActionButton) v.findViewById(R.id.fb_add_server);

        this.mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openConnectServerDialog(v);
            }
        });

//        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
////                mUserPresenter.getUserDetail(position);
////                User mUserList = (User) mUserAdapter.getItem(position);
////                Log.i("USER LIST BY ID ====> ", mUserList.getId() + "");
////
////
////                mUserPresenter.getUserDetail(mUserList.getId());
//            }
//        });
        return v;
    }

    public void openConnectServerDialog(View v)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//        View v = getLayoutInflater().inflate(R.layout.dialog_auth, null);

        EditText mIp = (EditText) v.findViewById(R.id.et_ipaddress);
        EditText mPort = (EditText) v.findViewById(R.id.et_port);
        EditText mUsername = (EditText) v.findViewById(R.id.et_username_server);
        EditText mPassword = (EditText) v.findViewById(R.id.et_password_server);
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

}
