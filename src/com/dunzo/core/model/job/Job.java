package com.dunzo.core.model.job;

import java.util.Date;

public class Job {
    public final String jobID;
    private final String objectName;
    private final String objectDescription;
    private final int objectDimension;
    private final String customerNumber;
    private final String riderNumber;
    private final String pickUpCode;
    private final String dropCode;
    private final String pickUpAddress;
    private final String dropAddress;
    private JobState jobState;
    private final double price;


    public Job(String jobID, String objectName, String objectDescription, int objectDimension,
               String customerNumber, String riderNumber, String pickUpCode,
               String dropCode, String pickUpAddress, String dropAddress,double price) {
        if (!pickUpCode.matches("[6]{1}[0-9]{5}"))
            throw new RuntimeException("Pickup pincode is not valid!");
        if (!dropCode.matches("[6]{1}[0-9]{5}"))
            throw new RuntimeException("Drop pincode is not valid!");
        this.jobID = jobID;
        this.objectName = objectName;
        this.objectDescription = objectDescription;
        this.objectDimension = objectDimension;
        this.jobState = new JobState.Assigned(new Date());
        this.customerNumber = customerNumber;
        this.riderNumber = riderNumber;
        this.pickUpCode = pickUpCode;
        this.dropCode = dropCode;
        this.pickUpAddress = pickUpAddress;
        this.dropAddress = dropAddress;
        this.price = price;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public int getObjectDimension() {
        return objectDimension;
    }

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getRiderNumber() {
        return riderNumber;
    }

    public String getPickUpCode() {
        return pickUpCode;
    }

    public String getDropCode() {
        return dropCode;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public double getPrice() {
        return price;
    }

}
