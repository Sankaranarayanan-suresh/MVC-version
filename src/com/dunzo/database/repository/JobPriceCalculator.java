package com.dunzo.database.repository;

public interface  JobPriceCalculator {
    double calculatePrice(String pickUpCode, String dropPincode, int objectDimension, double rating);
}
