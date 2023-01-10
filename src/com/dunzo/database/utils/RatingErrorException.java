package com.dunzo.database.utils;

public class RatingErrorException extends Exception {
    RatingErrorException(String message) {
        super(message);
    }

    RatingErrorException() {
        super();
    }
}
