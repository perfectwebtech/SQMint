package com.example.shalhan4.sqmint.ui.job;

import android.content.Context;

/**
 * Created by shalhan4 on 6/8/2017.
 */

public interface JobPresenterIntf {
    public void getJobDetail(int id);
    public String getAccessToken();
    public void setJobContext(Context context);
    public void startApi();
}
