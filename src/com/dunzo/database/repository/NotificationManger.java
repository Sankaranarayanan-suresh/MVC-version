package com.dunzo.database.repository;

import com.dunzo.core.model.notification.Notification;
import com.dunzo.core.model.user.User;

public class NotificationManger implements NotificationManager{
    @Override
    public void sendNotification(User user, String message) {
        Notification notification = new Notification(message);
        user.addNotification(notification);
    }
}
