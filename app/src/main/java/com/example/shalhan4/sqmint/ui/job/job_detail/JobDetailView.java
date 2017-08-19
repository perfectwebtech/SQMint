package com.example.shalhan4.sqmint.ui.job.job_detail;

import java.util.List;

/**
 * Created by shalhan4 on 6/8/2017.
 */

public interface JobDetailView {
    public void setUp();

    public void setJobDetailListAdapter(List<JobDetail> mJobDetailList);
    public void jobDetailListEmpty();

}
