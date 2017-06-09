package com.example.shalhan4.sqmint.ui.job.job_detail;

/**
 * Created by shalhan4 on 6/9/2017.
 */

public class JobDetail {
    private int id;
    private String jobName;
    private int jobId;
    private String runTime;
    private String runDate;
    private String duration;

    public JobDetail(int id, String jobName, int jobId, String runDate, String runTime, String duration) {
        this.id = id;
        this.jobName = jobName;
        this.jobId = jobId;
        this.runTime = runTime;
        this.runDate = runDate;
        this.duration = duration;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
