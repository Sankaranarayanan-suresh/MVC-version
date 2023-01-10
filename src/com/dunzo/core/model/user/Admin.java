package com.dunzo.core.model.user;

import com.dunzo.core.repository.user.AdminUserManager;
import com.dunzo.core.repository.user.UserDetailsManager;

import java.util.Collection;

public class Admin extends User {

    private final AdminUserManager manager;
    private final UserDetailsManager detailsManager;

    public Admin(String id, String name, String phoneNumber, String password, String emailID, AdminUserManager manager, UserDetailsManager detailsManager) {
        super(id, name, phoneNumber, password, emailID);
        this.manager = manager;
        this.detailsManager = detailsManager;
    }

    public void approveRider(Rider rider) {
        manager.approveRider(rider);
    }

    public void removeRider(Rider rider) {
        manager.removeRider(rider);
    }

    public Collection<Rider> getAllRiders() {
        return detailsManager.getAllRiders();
    }

}
