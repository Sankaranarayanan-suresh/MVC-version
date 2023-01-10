package com.dunzo.database.repository;

import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobBookingStatus;
import com.dunzo.core.model.job.JobState;
import com.dunzo.core.model.user.Customer;
import com.dunzo.core.model.user.Rider;
import com.dunzo.core.model.user.RiderStatus;
import com.dunzo.core.repository.job.CustomerJobManager;
import com.dunzo.core.repository.job.RiderJobManager;
import com.dunzo.core.repository.user.UserDetailsManager;
import com.dunzo.database.model.DatabaseFunctions;
import com.dunzo.database.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class JobDataManager implements CustomerJobManager, RiderJobManager, JobPriceCalculator {

    private final DatabaseFunctions<Job> database;
    private final UserDetailsManager manager;
    private final RiderDataManager riderDataManager;
    private final NotificationManager notificationManager;
    private final String[] invalidObj = {"jewel", "cash", "phone", "laptop", "mobile", "jewellery"};


    public JobDataManager(DatabaseFunctions<Job> database, UserDetailsManager manager, NotificationManager notificationManager, RiderDataManager riderDataManager) {
        this.database = database;
        this.manager = manager;
        this.riderDataManager = riderDataManager;
        this.notificationManager = notificationManager;
    }

    @Override
    public JobBookingStatus bookJob(String objectName, String objectDescription, int objectDimension,
                                    String pickUpPincode, String dropPincode, Customer customer,
                                    double requestedRatings, String pickUpAddress, String dropAddress) {
        List<Rider> riderList = new ArrayList<>(manager.getAllRiders());

        if (riderList.size() == 0) {
            return JobBookingStatus.NO_RIDER_AVAILABLE;
        }
        List<Rider> eligibleRiders = new ArrayList<>();
        for (Rider rider : riderList) {
            if (rider.getRatings() >= requestedRatings&& rider.isAvailable() && rider.getStatus().equals(RiderStatus.APPROVED))
                eligibleRiders.add(rider);
        }
        if (eligibleRiders.size() == 0) {
            return JobBookingStatus.NO_RIDER_AVAILABLE;
        }
        Random random = new Random();
        int riderIndex = random.nextInt(riderList.size());
        Rider assignedRider = riderList.get(riderIndex);
        if (checkObjectEligibility(objectName)) {
            return JobBookingStatus.OBJECT_CANNOT_BE_TRANSFERRED;
        }

        double price = calculatePrice(pickUpPincode,dropPincode,objectDimension,assignedRider.getRatings());

        Job job = new Job(Utils.generateID("job"), objectName, objectDescription
                , objectDimension, customer.getPhoneNumber(), assignedRider.getPhoneNumber()
                , pickUpPincode, dropPincode, pickUpAddress, dropAddress,price);
        database.set(job);


        String riderNotificationString = "Rider Name    : " + assignedRider.getName() +
                "\nRider Number : " + assignedRider.getPhoneNumber();

        notificationManager.sendNotification(customer, riderNotificationString);

        String customerNotificationString = "Customer Name    : " + customer.getName() +
                "\nCustomer Number : " + customer.getPhoneNumber();
        notificationManager.sendNotification(assignedRider, customerNotificationString);

        return JobBookingStatus.SUCCESS;

    }

    @Override
    public double calculatePrice(String pickUpCode, String dropPincode, int objectDimension, double rating) {

        double riderFee = 0, ratePerKm = 12;


        int[] riderFeeArr = {50, 100, 250, 400, 750, 1000};
        int index = (rating > 5 || rating < 0) ? 0 : (int) rating;
        riderFee = riderFeeArr[index];

        if (objectDimension <= 500)
            ratePerKm = 4;
        else if (objectDimension <= 1000)
            ratePerKm = 5;
        else if (objectDimension <= 1500)
            ratePerKm = 7;
        else if (objectDimension <= 2000)
            ratePerKm = 9;
        int pickUpPinCode = new Integer(pickUpCode);
        int dropCode = new Integer(dropPincode);
        return ((Math.abs(pickUpPinCode - dropCode)) * ratePerKm) + riderFee;
    }

    private boolean checkObjectEligibility(String objectName) {
        for (String obj : invalidObj) {
            if (objectName.equalsIgnoreCase(obj))
                return false;
        }
        return true;
    }


    @Override
    public Job getCurrentJob(String jobID) {
        return database.get(jobID);
    }

    @Override
    public List<Job> getHistoryOfJobs(String phoneNumber) {
        List<Job> allJobs = (List<Job>) database.getAll();
        List<Job> jobs = new ArrayList<>();
        for (Job job:allJobs){
            if (job.getCustomerNumber().equals(phoneNumber)){
                jobs.add(job);
            }
        }
        return jobs;
    }


    @Override
    public void cancelJob(Job job) {
        Job dbJob = database.get(job.jobID);
        dbJob.setJobState(new JobState.Cancelled(new Date()));
        Rider rider = (Rider) manager.getUser(job.getRiderNumber());
        Customer customer = (Customer) manager.getUser(job.getCustomerNumber());
        riderDataManager.changeRiderJob(rider, null);
        notificationManager.sendNotification(customer, "Your job " + job.jobID +
                " got cancelled. Try Booking it again.");
    }

    @Override
    public void pickUpObject(Job job) {
        Job dbJob = database.get(job.jobID);
        dbJob.setJobState(new JobState.PickedUP(new Date()));
        Rider rider = (Rider) manager.getUser(job.getRiderNumber());
        Customer customer = (Customer) manager.getUser(job.getCustomerNumber());
        riderDataManager.changeRiderJob(rider, job);
        notificationManager.sendNotification(customer, "Your job " + job.jobID +
                " has been picked-up.");
    }

    @Override
    public void deliverObject(Job job) {
        Job dbJob = database.get(job.jobID);
        dbJob.setJobState(new JobState.Delivered(new Date()));
        Rider rider = (Rider) manager.getUser(job.getRiderNumber());
        Customer customer = (Customer) manager.getUser(job.getCustomerNumber());
        riderDataManager.changeRiderJob(rider, null);
        notificationManager.sendNotification(customer, "Your job " + job.jobID +
                " has been delivered.");
    }

    @Override
    public void returnObject(Job job, String reason) {
        Job dbJob = database.get(job.jobID);
        dbJob.setJobState(new JobState.
                NotDelivered(new Date(), reason));
        Rider rider = (Rider) manager.getUser(job.getRiderNumber());
        Customer customer = (Customer) manager.getUser(job.getCustomerNumber());
        riderDataManager.changeRiderJob(rider, null);
        notificationManager.sendNotification(customer, "Your job " + job.jobID +
                " has been returned.\nReason: " + reason);
    }

}
