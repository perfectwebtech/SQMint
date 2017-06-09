package com.example.shalhan4.sqmint.ui.job;

/**
 * Created by shalhan4 on 6/8/2017.
 */

public class JobPresenter implements JobPresenterIntf{
    private JobView mJobView;

    public JobPresenter(JobView jobView)
    {
        this.mJobView = jobView;
    }


    @Override
    public void getJobDetail(int id) {
        this.mJobView.openJobDetail(id);
    }
}
