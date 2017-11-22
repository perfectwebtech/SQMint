package com.example.shalhan4.sqmint.ui.job;

/**
 * Created by shalhan4 on 6/7/2017.
 */

public class Job {
    private int id;
    private String jobId;
    private String jobName;
    private String lastRunDate;
    private String lastRunTime;
    private String lastRun;
    private String status;
    private int lastDuration;

    public Job()
    {

    }

    public int getLastDuration() {
        return lastDuration;
    }

    public void setLastDuration(int lastDuration) {
        this.lastDuration = lastDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getLastRun() {
        return lastRun;
    }

    public void setLastRun(String lastRun) {
        String[] splitted = lastRun.split("\\s+");
        setLastRunDate(splitted[0]);
        setLastRunTime(splitted[1]);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
