package com.dunzo.core.model.user;

import com.dunzo.core.repository.job.CustomerJobManager;
import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobBookingStatus;

import java.util.List;

public class Customer extends User {

    private final CustomerJobManager manager;

    public Customer(String id, String name, String phoneNumber, String password, String emailID, CustomerJobManager manager) {
        super(id, name, phoneNumber, password, emailID);
        this.manager = manager;
    }

    public JobBookingStatus bookJob(String objectName, String objectDescription, int objectDimension,
                                    String pickUpPincode, String dropPincode,double requestedRatings, String pickUpAddress,
                                    String dropAddress) {
       return manager.bookJob(objectName,objectDescription,objectDimension,pickUpPincode,
                dropPincode,this,requestedRatings,pickUpAddress,dropAddress);

    }

    public Job getCurrentJobInfo(String jobID) {
        return manager.getCurrentJob(jobID);
    }
    public List<Job> getAllJob(){
       return manager.getHistoryOfJobs(this.getPhoneNumber());
    }

}
