package com.example.shalhan4.sqmint.ui.job;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;

import java.util.List;

/**
 * Created by shalhan4 on 6/7/2017.
 */

public class JobListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Job> mJobList;

    public JobListAdapter(Context context, List<Job> mJobList) {
        this.mContext = context;
        this.mJobList = mJobList;
    }

    @Override
    public int getCount() {
        return mJobList.size();
    }

    @Override
    public Object getItem(int position) {
        return mJobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.job_list, null);

        TextView tvJobName = (TextView) v.findViewById(R.id.tv_job_name);
        TextView tvLastRunDate = (TextView) v.findViewById(R.id.tv_last_run_date);
        TextView tvLastRunTime = (TextView) v.findViewById(R.id.tv_last_run_time);
        ImageView ivJobStatus = (ImageView) v.findViewById(R.id.iv_job_status);

        tvJobName.setText(this.mJobList.get(position).getJobName());
        tvLastRunDate.setText(this.mJobList.get(position).getLastRunDate());
        tvLastRunTime.setText(this.mJobList.get(position).getLastRunTime());

        if(this.mJobList.get(position).getStatus().equals("Succeeded")) {
            ivJobStatus.setImageResource(R.drawable.list_success);
        }
        else{
            ivJobStatus.setImageResource(R.drawable.list_error);
        }

        v.setTag(mJobList.get(position).getId());

        return v;
    }
}
