package com.example.shalhan4.sqmint.ui.server;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment implements ServerView {
    private FloatingActionButton mFab;
    private ListView mListView;
    private ServerListAdapter mServerAdapter;
    private ServerPresenter mServerPresenter;

    public ServerFragment()
    {
        this.mServerPresenter = new ServerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_server, container, false);

        this.mServerPresenter.setServerContext(getActivity());
        this.mServerPresenter.startApi();

        this.mListView = (ListView) v.findViewById(R.id.lv_server_list);

        this.mFab = (FloatingActionButton) v.findViewById(R.id.fb_add_server);
        this.mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openConnectServerDialog();
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

    private AlertDialog connectServerDialog;
    public void openConnectServerDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_server, null);

        final EditText mIp = (EditText) v.findViewById(R.id.et_ipaddress);
        final EditText mUsername = (EditText) v.findViewById(R.id.et_username_server);
        final EditText mPassword = (EditText) v.findViewById(R.id.et_password_server);
        Button mBtnLogin = (Button) v.findViewById(R.id.btn_connect_server);

        mBuilder.setView(v);
        this.connectServerDialog = mBuilder.create();
        this.connectServerDialog.show();

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Log.i("HALO => ", "HALO JUGA");
                mServerPresenter.addServer(mIp.getText().toString(), mUsername.getText().toString(), mPassword.getText().toString() );
            }
        });
    }

    private AlertDialog alertDialog;
    public void openAlertDialog(int serverId)
    {
        final int selectedServerId = serverId;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_alert, null);

        TextView mMessage = (TextView) v.findViewById(R.id.tv_message);
        Button bYes = (Button) v.findViewById(R.id.btn_alert_yes);
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerPresenter.deleteServer(selectedServerId);
            }
        });

        mBuilder.setView(v);
        this.alertDialog = mBuilder.create();
        this.alertDialog.show();
    }

    @Override
    public void setServerListAdapter(List<Server> mServerList) {
        this.mServerAdapter = new ServerListAdapter(getActivity(), mServerList, this);
        this.mListView.setAdapter(mServerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void deleteServer(int id)
    {
        this.openAlertDialog(id);
        Log.i("CLICK DI FRAGMENT", "YEAY " + id);
    }

    @Override
    public void deleteSuccess() {
        Log.i("Yeayy berhasil masuk", "yeay");
        this.mServerPresenter.startApi();
        this.alertDialog.dismiss();
    }

    @Override
    public void deleteFailed() {
        Log.i("Yeayy berhasil failed", "yeay");
        this.mServerPresenter.startApi();
        this.alertDialog.dismiss();
    }

    @Override
    public void addServerSuccess() {
        Log.i("Yeayy berhasil sukses", "yeay");
        this.mServerPresenter.startApi();
        this.connectServerDialog.dismiss();
    }

    @Override
    public void addServerFailed() {
        Log.i("Yeayy berhasil failed", "yeay");
        this.mServerPresenter.startApi();
        this.connectServerDialog.dismiss();
    }
}
