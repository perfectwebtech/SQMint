package com.example.shalhan4.sqmint.ui.job;

/**
 * Created by shalhan4 on 6/7/2017.
 */

public class Job {
    private int id;
    private String jobName;
    private String lastRunDate;
    private String lastRunTime;

    public Job(int id, String jobName, String lastRunDate, String lastRunTime)
    {
        this.id = id;
        this.jobName = jobName;
        this.lastRunDate = lastRunDate;
        this.lastRunTime = lastRunTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(String lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public String getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(String lastRunTime) {
        this.lastRunTime = lastRunTime;
    }
}
