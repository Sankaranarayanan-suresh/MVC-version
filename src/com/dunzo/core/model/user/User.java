package com.dunzo.core.model.user;

import com.dunzo.core.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class User {

    public final String id;
    private String name;
    private final String phoneNumber;
    private String emailID;
    private  String password;
    private final List<Notification> notifications;

    public User(String id, String name, String phoneNumber, String password, String emailID) {
        if(!phoneNumber.matches("[6-9]{1}[0-9]{9}")) {
            throw new RuntimeException("Phone number is not valid");
        }
        if(!emailID.matches(".*@.*\\.(com|org)")) {
            throw new RuntimeException("Email ID is not valid");
        }
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.emailID = emailID;
        this.notifications = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public List<Notification> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public void addNotification(Notification notification){
        notifications.add(notification);
    }

    public void removeNotification(Notification notification){
        notifications.remove(notification);
    }

}
