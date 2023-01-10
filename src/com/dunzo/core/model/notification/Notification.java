package com.dunzo.core.model.notification;

public class Notification {
    private final String notificationMessage;

    public Notification(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

}
