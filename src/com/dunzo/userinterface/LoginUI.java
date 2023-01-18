package com.dunzo.userinterface;

import com.dunzo.core.model.user.Admin;
import com.dunzo.core.model.user.Customer;
import com.dunzo.core.model.user.Rider;
import com.dunzo.core.model.user.User;
import com.dunzo.database.repository.JobDataManager;
import com.dunzo.database.repository.UserDataManager;

import java.util.Scanner;

public class LoginUI {
    private final UserDataManager userDataManager;
    private final JobDataManager jobDataManager;
    public LoginUI(UserDataManager userDataManager, JobDataManager jobDataManager) {
        this.userDataManager = userDataManager;
        this.jobDataManager = jobDataManager;
    }

    public User signUp(String user) {
        System.out.println("Enter Your name: ");
        String name = new Scanner(System.in).nextLine();

        System.err.println("Remember the phone number that you enter now. \nYou will be using your phone number to login again.");
        System.out.println("Enter Your phone number: ");
        String phoneNumber = new Scanner(System.in).nextLine();

        if (userDataManager.userExists(phoneNumber)) {
            System.out.println("User already Exists!!!");
            signUp(user);
        }
        System.out.println("Enter Your Email-ID: ");
        String mail = new Scanner(System.in).nextLine();

        while (true) {
            System.out.println("Enter password for your account: ");
            String password = new Scanner(System.in).nextLine();
            System.out.println("Re-Enter your password: ");
            String reenteredPassword = new Scanner(System.in).nextLine();
            if (!password.equals(reenteredPassword)) {
                System.err.println("Password Mismatch");
                continue;
            }
            if (user.equalsIgnoreCase("customer")) {
                return userDataManager.addNewCustomer(name, phoneNumber, password, mail, jobDataManager);
            }
            if (user.equalsIgnoreCase("rider")) {
                System.out.println("Enter your service location: ");
                String serviceLocation = new Scanner(System.in).nextLine();
                System.out.println("\nYour application has been submitted!\n" +
                        "Use the Given phone Number and password to login to know about your application status.");
                return userDataManager.addNewRider(name, phoneNumber, password, mail, serviceLocation, jobDataManager);
            }
        }
    }
    public User signIn(String user){
        while (true) {
            System.out.println("Enter Your phone number: ");
            String phoneNumber = new Scanner(System.in).nextLine();
            System.out.println("Enter the password: ");
            String password = new Scanner(System.in).nextLine();
            if (userDataManager.getUser(phoneNumber) instanceof Customer) {
                if (userDataManager.checkUserCredentials(phoneNumber, password)) {
                    System.out.println("\n\nLogin Successfully Completed!!");
                    return userDataManager.getUser(phoneNumber);
                } else {
                    System.err.println("Incorrect credentials!!");
                }
            }else if (userDataManager.getUser(phoneNumber) instanceof Rider){
                if (userDataManager.checkUserCredentials(phoneNumber, password)) {
                    System.out.println("\n\nLogin Successfully Completed!!");
                    return userDataManager.getUser(phoneNumber);
                } else {
                    System.err.println("Incorrect credentials!!");
                }
            }else if (userDataManager.getUser(phoneNumber) instanceof Admin){
                if (userDataManager.checkUserCredentials(phoneNumber, password)) {
                    System.out.println("\n\nLogin Successfully Completed!!");
                    return userDataManager.getUser(phoneNumber);
                } else {
                    System.err.println("Incorrect credentials!!");
                }
            }
            else {
                System.err.println("No such User exists!!");
                return null;
            }
        }
    }
}
