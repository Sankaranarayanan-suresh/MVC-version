package com.dunzo.userinterface;

import com.dunzo.core.model.notification.Notification;
import com.dunzo.core.model.user.Admin;
import com.dunzo.core.model.user.Rider;
import com.dunzo.core.model.user.RiderStatus;
import com.dunzo.database.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final Admin admin;

    public AdminUI(Admin admin) {
        if (admin == null) {
            throw new RuntimeException("Unable to create UI.");
        }
        this.admin = admin;
    }

    private void editProfile() {
        editProfile:
        while (true) {
            System.out.println("1.Change Name\n2.Change Mail-ID\n" +
                    "3.Change password\n4.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference) {
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    admin.setName(newName);
                    //admin.changeName(this.getPhoneNumber(), newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    admin.setEmailID(newMailId);
                    //customerRequest.changeMailID(this.getPhoneNumber(), newMailId);
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
                                admin.setPassword(newPassword);
                                break;
                            }
                            System.out.println("Old password and new password are teh same please change your password");
                        }
                        //customerRequest.changePassword(oldPassword, newPassword, this.getPhoneNumber());
                    }
                case 4:
                    break editProfile;

            }
        }
    }

    private void addRider() {
        System.out.println(admin.getAllRiders().size());
        if (admin.getAllRiders().size() == 0) {
            System.out.println("\nNo riders to add\n");
            return;
        }

        List<Rider> toApprove = new ArrayList<>();
        admin.getAllRiders().forEach(rider -> {
            if(rider.getStatus().equals(RiderStatus.NOT_APPROVED)) {
                toApprove.add(rider);
            }
        });

        for(Rider rider : toApprove) {
            System.out.println(rider);
            System.out.println("1.Add to rider database\n2.Remove from the list\n3.Skip");
            int adminDecision = Utils.getInteger();
            if (adminDecision == 3) {
                continue;
            }
            if (adminDecision == 1) {
                admin.approveRider(rider);
                System.out.println("Rider Added to database.");
            } else {
                admin.removeRider(rider);
                System.out.println("Rider removed from database.");
            }
        }


    }

    private void removeRider() {
        if (admin.getAllRiders().size() > 0) {
            int i = 1;
            for (Rider rider : admin.getAllRiders()) {
                System.out.println(i + ".\t" + rider.getName() + "\t: " + rider.getRatings());
            }
            System.out.println("Select the rider you want to remove! or press 0 to exit");
            int riderIndex = Utils.getInteger();
            if (riderIndex > 0) {
                try {
                    Rider rider = ((List<Rider>) admin.getAllRiders()).get(riderIndex - 1);
                    admin.removeRider(rider);
                } catch (Exception e) {
                    System.out.println("You cannot remove a rider who does not exist.\nPlease select from given option.");
                    removeRider();
                }
            }
        } else {
            System.out.println("\nNo rider to remove!!\n");
        }

    }

    private void viewNotification() {
        if (admin.getNotifications().size() > 0) {
            for (Notification notification : admin.getNotifications()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {

                }
                System.out.println(notification.getNotificationMessage());
            }
            admin.getNotifications().clear();
        } else {
            System.out.println("You have no new notifications!!\n");
        }
    }

    private void viewProfile() {
        System.out.println(this);
    }

    public void showMenu() {
        System.out.println("You have " + admin.getNotifications().size() + " notifications");
        driverFunction:
        while (true) {
            System.out.println("1.Edit Profile\n2.Add Riders\n3.Remove Rider\n4.View Notifications\n5.View my Profile\n" +
                    "6.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    addRider();
                    break;
                case 3:
                    removeRider();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    break driverFunction;
            }
        }
    }
}
