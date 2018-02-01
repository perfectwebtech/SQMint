package com.example.shalhan4.sqmint.ui.job.job_detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;

import java.util.List;

/**
 * Created by shalhan4 on 6/9/2017.
 */

public class JobDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private List<JobDetail> mJobDetailList;

    public JobDetailListAdapter(Context context, List<JobDetail> jobDetailList) {
        this.mContext = context;
        this.mJobDetailList = jobDetailList;
    }

    @Override
    public int getCount() {
        return this.mJobDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mJobDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(this.mContext, R.layout.job_detail_list, null);

        TextView tvRunDate = (TextView) v.findViewById(R.id.tv_login_date);
        TextView tvRunTime = (TextView) v.findViewById(R.id.tv_login_time);
        TextView tvDuration = (TextView) v.findViewById(R.id.tv_duration);
        ImageView ivJobStatus = (ImageView) v.findViewById(R.id.iv_job_detail_status);

        tvRunDate.setText(this.mJobDetailList.get(position).getRunDate());
        tvRunTime.setText(this.mJobDetailList.get(position).getRunTime());
        tvDuration.setText(this.mJobDetailList.get(position).getDuration() + " sec");

        if(this.mJobDetailList.get(position).getStatus().equals("Succeeded")) {
            ivJobStatus.setImageResource(R.drawable.list_success);
        }
        else{
            ivJobStatus.setImageResource(R.drawable.list_error);
        }

        v.setTag(mJobDetailList.get(position).getId());

        return v;
    }
}
