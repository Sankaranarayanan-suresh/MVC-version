package com.dunzo.core.model.user;

import com.dunzo.core.repository.job.RiderJobManager;
import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobState;

import java.util.List;

public class Rider extends User {

    private final String serviceLocation;
    private boolean available;
    private double ratings;
    private RiderStatus status;
    private Job currentJob;
    private final RiderJobManager manager;

    public Rider(String id, String name, String phoneNumber, String password, String emailID, String serviceLocation, RiderJobManager manager) {
        super(id, name, phoneNumber, password, emailID);
        if(!serviceLocation.matches("[6]{1}[0-9]{5}")) {
            throw new RuntimeException("Invalid service location");
        }
        this.serviceLocation = serviceLocation;
        available = true;
        this.status = RiderStatus.NOT_APPROVED;
        this.currentJob = null;
        this.manager = manager;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        if (this.ratings + ratings >= 5) {
            return;
        }
        this.ratings = ratings;
    }

    public RiderStatus getStatus() {
        return status;
    }

    public void setStatus(RiderStatus status) {
        this.status = status;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public void pickUpObject() {
        if(this.currentJob != null && this.currentJob.getJobState() instanceof JobState.Assigned) {
            manager.pickUpObject(this.currentJob);
            return;
        }
        throw new RuntimeException("You are not assigned with a job. You cannot pick-up a job.");

    }

    public void deliverObject() {
        if(this.currentJob != null && this.currentJob.getJobState() instanceof JobState.PickedUP) {
            manager.deliverObject(this.currentJob);
            return;
        }
        throw new RuntimeException("You don't have any job to deliver.");


    }

    public void returnObject(String reason) {
        if(this.currentJob != null && this.currentJob.getJobState() instanceof JobState.PickedUP) {
            manager.returnObject(this.currentJob,reason);
            return;
        }
        throw new RuntimeException("You don't have any job to return.");
    }

    public void cancelJob() {
        if(this.currentJob != null && this.currentJob.getJobState() instanceof JobState.Assigned) {
            manager.cancelJob(this.currentJob);
            return;
        }
        throw new RuntimeException("You don't have any job to cancel.");
    }
    public List<Job> getAllJob(){
        return manager.getHistoryOfJobs(this.getPhoneNumber());
    }
}
