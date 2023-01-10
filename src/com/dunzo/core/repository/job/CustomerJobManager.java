package com.dunzo.core.repository.job;

import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobBookingStatus;
import com.dunzo.core.model.user.Customer;

import java.util.List;

public interface CustomerJobManager {

    JobBookingStatus bookJob(String objectName, String objectDescription, int objectDimension,
                             String pickUpPincode, String dropPincode, Customer customer, double requestedRatings,
                             String pickUpAddress, String dropAddress);
    Job getCurrentJob(String jobID);
    List<Job> getHistoryOfJobs(String phoneNumber);
}
