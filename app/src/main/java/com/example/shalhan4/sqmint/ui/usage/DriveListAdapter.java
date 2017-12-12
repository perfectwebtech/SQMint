package com.example.shalhan4.sqmint.ui.usage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.server.Server;
import com.example.shalhan4.sqmint.ui.server.ServerView;

import java.util.List;

/**
 * Created by shalhan4 on 12/12/2017.
 */

public class DriveListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Drive> mDriveList;
    private UsageView mUsageView;

    public DriveListAdapter(Context context, List<Drive> driveList)
    {
        this.mContext = context;
        this.mDriveList = driveList;
    }

    @Override
    public int getCount() {
        Log.i("DRIVE SIZE ", this.mDriveList.size() + " ");
        return this.mDriveList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mDriveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.drive_list, null);

        TextView tvDriveName = (TextView) v.findViewById(R.id.tv_drive_name);
        TextView tvAvailableSpace = (TextView) v.findViewById(R.id.tv_available_space);
        TextView tvTotalSpace = (TextView) v.findViewById(R.id.tv_total_space);


        tvDriveName.setText(this.mDriveList.get(position).getDriveName() + "://");
        tvAvailableSpace.setText(this.mDriveList.get(position).getAvailableSpace() + " GB");
        tvTotalSpace.setText(this.mDriveList.get(position).getTotalSpace() + " GB");

        return v;
    }
}
