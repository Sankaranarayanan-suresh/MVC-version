package com.dunzo.userinterface;

import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.job.JobBookingStatus;
import com.dunzo.core.model.notification.Notification;
import com.dunzo.core.model.user.Customer;
import com.dunzo.database.repository.JobPriceCalculator;
import com.dunzo.database.utils.Utils;

import java.util.Scanner;

public class CustomerUI {
    private final Customer customer;
    private final JobPriceCalculator priceCalculator;

    public CustomerUI(Customer customer, JobPriceCalculator priceCalculator) {
        if (customer == null) {
            throw new RuntimeException("Unable to create UI.");
        }
        this.customer = customer;
        this.priceCalculator = priceCalculator;
    }

    private void bookService() {
        System.out.print("Enter Object Name: ");
        String objectName = new Scanner(System.in).nextLine();

        System.out.print("Enter Object description(in max of 50 words): ");
        String objectDescription = new Scanner(System.in).nextLine();

        System.out.print("Enter Object Dimension(area): ");
        int objectDimension = Utils.getInteger();

        System.out.print("Enter pick-up location address: ");
        String pickUpAddress = new Scanner(System.in).nextLine();

        System.out.print("Enter pick-up location pincode: ");
        String pickUpPincode = new Scanner(System.in).nextLine();

        System.out.print("Enter drop location address: ");
        String dropAddress = new Scanner(System.in).nextLine();

        System.out.print("Enter Drop location pincode: ");
        String dropPincode = new Scanner(System.in).nextLine();

        System.out.println("Do you need any specific rating for the rider?(y/n)");
        double rating = 0;
        double actualPrice = 0;
        String ratingPreference = new Scanner(System.in).nextLine();
        if (ratingPreference.equalsIgnoreCase("y")) {
            System.out.println("Enter minimum rating: ");
            rating = Utils.getRatings();
            actualPrice = priceCalculator.calculatePrice(pickUpPincode, dropPincode, objectDimension, (int) rating);
        } else if (ratingPreference.equalsIgnoreCase("n")) {
            actualPrice = priceCalculator.calculatePrice(pickUpPincode, dropPincode, objectDimension, 0);
        }
        System.out.println("Confirm Booking?(y/n)");
        String confirmBooking = new Scanner(System.in).nextLine();
        if (confirmBooking.equalsIgnoreCase("y")) {
            if (customer.bookJob(objectName, objectDescription, objectDimension,
                    pickUpPincode, dropPincode, rating, pickUpAddress, dropAddress).equals(JobBookingStatus.SUCCESS)) {
                System.out.println("Initializing Payment process...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                payment(actualPrice);
            }
        }
    }

    private void trackOrder() {
        String jobID = new Scanner(System.in).nextLine();
        Job myCurrentJob = customer.getCurrentJobInfo(jobID);
        if (myCurrentJob == null){
            System.out.println("You don't have any jobs currently running.");
        }
        System.out.println(myCurrentJob + "\n");
    }

    private void payment(double actualAmount) {
        System.out.print("Enter the amount: Rs.");
        double amount = Utils.getAmount();
        if (amount == actualAmount) {
            System.out.println("Payment Successful:)");
            return;
        }
        System.err.println("Incorrect amount!!!!");
        System.out.println();
        payment(actualAmount);

    }
    private void viewNotification() {
        if (customer.getNotifications().size()>0){
            for (Notification notification : customer.getNotifications()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {

                }
                System.out.println(notification.getNotificationMessage());
            }
            customer.getNotifications().clear();
        }else {
            System.out.println("You have no new notifications!!\n");
        }
    }

    private void viewProfile() {
        System.out.println(this);
    }
    private void viewHistory() {
        System.out.println(customer.getAllJob());
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
                    customer.setName(newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    customer.setEmailID(newMailId);
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
                                customer.setPassword(newPassword);
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

    public void showMenu() {
        System.out.println("You have " + customer.getNotifications().size() + " notifications");
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.Book a service\n3.Track My Job\n4.View Notifications\n5.View my Profile\n" +
                    "6.View History\n7.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    bookService();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    viewHistory();
                    break;
                case 7:
                    break driverFunction;
            }
        }
    }

}
