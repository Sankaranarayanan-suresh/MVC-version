package com.dunzo.core.repository.user;

import com.dunzo.core.model.user.Rider;
import com.dunzo.core.model.user.User;

import java.util.Collection;

public interface UserDetailsManager {

    Collection<Rider> getAllRiders();

    Collection<User> getAllUsers();

    User getUser(String phoneNumber);

    boolean checkUserCredentials(String phoneNumber, String password);

    boolean userExists(String phoneNumber);
}

