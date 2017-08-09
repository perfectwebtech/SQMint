package com.example.shalhan4.sqmint.ui.job;

import android.content.Context;

import java.util.List;

/**
 * Created by shalhan4 on 6/8/2017.
 */

public interface JobView {
    public void openJobDetail(int id);
    public void setJobListAdapter(List<Job> mJobList);

}
