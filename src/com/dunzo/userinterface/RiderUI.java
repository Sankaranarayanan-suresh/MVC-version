package com.dunzo.userinterface;

import com.dunzo.core.model.job.JobState;
import com.dunzo.core.model.notification.Notification;
import com.dunzo.core.model.user.Rider;
import com.dunzo.database.utils.Utils;

import java.util.Scanner;

public class RiderUI {
    private final Rider rider;

    public RiderUI(Rider rider) {
        if (rider == null){
            throw new RuntimeException("Unable to create UI.");
        }
        this.rider = rider;
    }
    public void editProfile() {
        editProfile:
        while (true) {
            System.out.println("1.Change Name\n2.Change Mail-ID\n" +
                    "3.Change password\n4.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference) {
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    rider.setName(newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    rider.setEmailID(newMailId);
                    System.out.println("Your EmailId has been changed successfully:)");
                    break;
                case 3:
                    System.err.println("Changing your Password can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String passwordConfirmation = new Scanner(System.in).nextLine();
                    if (passwordConfirmation.equalsIgnoreCase("y")) {
                        while (true) {
                            System.out.println("Enter your old password: ");
                            String oldPassword = new Scanner(System.in).nextLine();
                            System.out.println("Enter your New Password: ");
                            String newPassword = new Scanner(System.in).nextLine();
                            if (!oldPassword.equals(newPassword)) {
                                rider.setPassword(newPassword);
                                break;
                            }
                            System.out.println("Old password and new password are teh same please change your password");
                        }
                    }
                case 4:
                    break editProfile;

            }
        }

    }
    private void viewJob() {
        try {
            System.out.println(rider.getCurrentJob().toString());
            System.out.println("1.Change state of the object\n2.Exit");
            int riderDecision = Utils.getInteger();
            int objectState;
            if (riderDecision == 1) {
                if (rider.getCurrentJob().getJobState() instanceof JobState.Assigned) {
                    System.out.println("1.Picked-Up\n2.Exit");
                    objectState = Utils.getInteger();
                    if (objectState == 1) {
                        rider.pickUpObject();
                    }
                } else if (rider.getCurrentJob().getJobState() instanceof JobState.PickedUP) {
                    System.out.println("1.Delivered\n2.Not Delivered\n3.Exit");
                    objectState = Utils.getInteger();
                    if (objectState == 1) {
                        rider.deliverObject();
                    } else if (objectState == 2) {
                        System.out.println("State the exact reason: ");
                        String reason = new Scanner(System.in).nextLine();
                        rider.returnObject(reason);
                    } else if (objectState == 3) {
                        return;
                    }
                }

            }
        } catch (NullPointerException e) {
            System.out.println("Currently You have no jobs to complete.");
        }
    }
    private void cancelJob() {
        if (rider.getCurrentJob() == null) {
            System.out.println("You have no job.");
            return;
        }
        System.out.println("Do you want to cancel the job");
        System.out.println("1.Cancel\n2.Exit");
        int riderPreference = Utils.getInteger();
        if (riderPreference == 1) {
            rider.cancelJob();
        }
    }
    private void viewNotification() {
        if (rider.getNotifications().size()>0){
            for (Notification notification : rider.getNotifications()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {

                }
                System.out.println(notification.getNotificationMessage());
            }
            rider.getNotifications().clear();
        }else {
            System.out.println("You have no new notifications!!\n");
        }
    }
    private void viewProfile() {
        System.out.println(this);
    }
    private void viewHistory() {
        System.out.println(rider.getAllJob());
    }

    public void showMenu() {
        System.out.println("You have " + rider.getNotifications().size() + " notifications");
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.View Job\n3.Cancel Job \n4.View Notification\n5.View my Profile\n" +
                    "6.View History\n7.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    viewJob();
                    break;
                case 3:
                    cancelJob();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    viewHistory();
                case 7:
                    break driverFunction;
            }
        }
    }
}
