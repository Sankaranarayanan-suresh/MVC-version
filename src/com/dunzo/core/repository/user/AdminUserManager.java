package com.dunzo.core.repository.user;

import com.dunzo.core.model.user.Rider;

public interface AdminUserManager {
    void approveRider(Rider rider);

    void removeRider(Rider rider);
}
