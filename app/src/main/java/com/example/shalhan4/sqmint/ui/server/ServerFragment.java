package com.example.shalhan4.sqmint.ui.server;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;
import com.example.shalhan4.sqmint.ui.monitoring.MonitoringActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment implements ServerView {
    private FloatingActionButton mFab;
    private ListView mListView;
    private ServerListAdapter mServerAdapter;
    private ServerPresenter mServerPresenter;
    SharedPreferences sharedPreferences;


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

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.mFab = (FloatingActionButton) v.findViewById(R.id.fb_add_server);

        if(!this.sharedPreferences.getString("STATUS", null).equals("SUPERADMIN")) {
            this.mFab.setVisibility(v.INVISIBLE);
        }
        else
        {
            this.mFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openConnectServerDialog();
                }
            });
        }
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Server mServerList = (Server) mServerAdapter.getItem(position);
                Log.i("SERVER ID ==> ", mServerList.getId() + "");
                mServerPresenter.openMonitoring(mServerList.getId(), mServerList.getIpAddress());
            }
        });
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
        final Button mBtnLogin = (Button) v.findViewById(R.id.btn_connect_server);

        mBuilder.setView(v);
        this.connectServerDialog = mBuilder.create();
        this.connectServerDialog.show();

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Log.i("HALO => ", "HALO JUGA");
                mBtnLogin.setEnabled(false);
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
        Toast.makeText(getActivity(), "Delete server failed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void deleteFailed() {
        Log.i("Yeayy berhasil failed", "yeay");
        this.mServerPresenter.startApi();
        this.alertDialog.dismiss();
        Toast.makeText(getActivity(), "Delete server failed, please check your connection and try again", Toast.LENGTH_LONG).show();

    }

    @Override
    public void addServerSuccess() {
        Log.i("Yeayy berhasil sukses", "yeay");
        this.mServerPresenter.startApi();
        this.connectServerDialog.dismiss();
        Toast.makeText(getActivity(), "Add server success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void addServerFailed() {
        Log.i("Yeayy berhasil failed", "yeay");
        this.mServerPresenter.startApi();
        this.connectServerDialog.dismiss();
        Toast.makeText(getActivity(), "Add server failed, please check your connection and try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void openMonitoringPage(int id, String ipAddress) {
        Intent intent = new Intent(getActivity(), MonitoringActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("SERVER_ID", id);
        extras.putString("IP_ADDRESS", ipAddress);
        intent.putExtras(extras);
        startActivity(intent);


        Log.i("MONITORING => ", id + " " + ipAddress);
    }
}
