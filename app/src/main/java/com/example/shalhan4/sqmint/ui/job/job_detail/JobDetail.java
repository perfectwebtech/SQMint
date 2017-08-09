package com.example.shalhan4.sqmint.ui.job.job_detail;

import com.example.shalhan4.sqmint.ui.job.Job;

import java.util.List;

/**
 * Created by shalhan4 on 6/9/2017.
 */

public class JobDetail{
    private int id;
    private String jobName;
    private String runTime;
    private String runDate;
    private String duration;
    private String status;
    private List<Job> mJobList;

    public JobDetail(){

    }

    public JobDetail(int id, String runDate, String runTime, String duration) {
        this.id = id;
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

    public void setRunDateTime(String lastRun) {
        String[] splitted = lastRun.split("\\s+");
        setRunDate(splitted[0]);
        setRunTime(splitted[1]);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Job> getJobList() {
        return mJobList;
    }

    public void setJobList(List<Job> mJobList) {
        this.mJobList = mJobList;
    }
}
