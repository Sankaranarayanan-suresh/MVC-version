package com.dunzo.database.repository;

import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobState;
import com.dunzo.core.model.user.*;
import com.dunzo.core.repository.job.CustomerJobManager;
import com.dunzo.core.repository.job.RiderJobManager;
import com.dunzo.core.repository.user.AdminUserManager;
import com.dunzo.core.repository.user.UserDetailsManager;
import com.dunzo.database.model.DatabaseFunctions;
import com.dunzo.database.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDataManager implements AdminUserManager, UserDetailsManager, RiderDataManager {

    private final DatabaseFunctions<User> database;
    private final NotificationManager notificationManager;

    public UserDataManager(DatabaseFunctions<User> database,NotificationManager notificationManager) {
        this.database = database;
        this.notificationManager = notificationManager;
    }

    @Override
    public void approveRider(Rider rider) {
        User user = database.get(rider.getPhoneNumber());
        if (!(user instanceof Rider)) return;
        Rider dbRider = (Rider) user;
        dbRider.setStatus(RiderStatus.APPROVED);
        database.update(dbRider);
    }

    @Override
    public void removeRider(Rider rider) {
        database.remove(rider);
    }

    @Override
    public void changeRiderJob(Rider rider, Job job) {
        Rider dbRider = (Rider) database.get(rider.getPhoneNumber());
        if (job.getJobState() instanceof JobState.Cancelled) {
            dbRider.setRatings(rider.getRatings() - 0.25);
        }
        if (job.getJobState() instanceof JobState.Delivered) {
            dbRider.setRatings(rider.getRatings() + 0.5);
        }
        dbRider.setCurrentJob(job);
    }

    @Override
    public Collection<Rider> getAllRiders() {
        List<User> allUsers = new ArrayList<>(database.getAll());
        List<Rider> availableRiders = new ArrayList<>();
        for (User user : allUsers) {
            if (user instanceof Rider) {
                availableRiders.add((Rider) user);
            }
        }
        return availableRiders;
    }

    @Override
    public Collection<User> getAllUsers() {
        return database.getAll();
    }

    @Override
    public User getUser(String phoneNumber) {
        return database.get(phoneNumber);
    }

    @Override
    public boolean checkUserCredentials(String phoneNumber, String password) {
        return database.get(phoneNumber).getPassword().equals(password);
    }

    @Override
    public boolean userExists(String phoneNumber) {
        for (User user : database.getAll()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }

        }
        return false;
    }

    public Customer addNewCustomer(String name, String phoneNumber, String password, String emailID, CustomerJobManager manager) {
        Customer customer = new Customer(Utils.generateID("Customer"), name, phoneNumber, password, emailID, manager);
        database.set(customer);
        return customer;
    }

    public Rider addNewRider(String name, String phoneNumber, String password, String emailID, String serviceLocation, RiderJobManager manager) {
        Rider rider = new Rider(Utils.generateID("Rider"), name, phoneNumber, password, emailID, serviceLocation, manager);
        database.set(rider);

        Admin admin = null;
        for(User users : this.getAllUsers()){
            if (users instanceof Admin){
                admin = (Admin) users;
            }
        }
        notificationManager.sendNotification(admin,name+ " has requested as rider.");
        return rider;
    }

    public Admin addNewAdmin(String name, String phoneNumber, String password, String emailID, AdminUserManager manager, UserDetailsManager detailsManager) {
        Admin admin = new Admin(Utils.generateID("admin"), name, phoneNumber, password, emailID, manager, detailsManager);
        database.set(admin);
        return admin;
    }


}
