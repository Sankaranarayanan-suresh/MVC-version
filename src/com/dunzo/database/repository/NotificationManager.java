package com.dunzo.database.repository;

import com.dunzo.core.model.user.User;

public interface NotificationManager {
    void sendNotification(User user ,String message);
}
