package com.dunzo.core.repository.job;

import com.dunzo.core.model.job.Job;

import java.util.List;

public interface RiderJobManager {
    void cancelJob(Job job);
    void pickUpObject(Job job);
    void deliverObject(Job job);
    void returnObject(Job job,String reason) ;
    List<Job> getHistoryOfJobs(String phoneNumber);
}
