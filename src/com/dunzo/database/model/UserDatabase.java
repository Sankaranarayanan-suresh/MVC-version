package com.dunzo.database.model;

import com.dunzo.core.model.user.User;

import java.util.Collection;
import java.util.HashMap;

public class UserDatabase implements DatabaseFunctions<User> {
    private static UserDatabase databaseInstance = null;
    public static UserDatabase getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new UserDatabase();
        }
        return databaseInstance;
    }


    private UserDatabase() {
    }
    private final HashMap<String, User> userData = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return userData.values();
    }

    @Override
    public void set(User userData) {
        if (this.userData.containsKey(userData.getPhoneNumber())){
            throw new RuntimeException("User Already Exists");
        }
        this.userData.put(userData.getPhoneNumber(),userData);
        System.out.println(this.userData.size());
    }

    @Override
    public User get(String key) {
        return userData.get(key);
    }

    @Override
    public void update(User userData) {
        if (!this.userData.containsKey(userData.getPhoneNumber())){
            throw new RuntimeException("The data you are trying to update does not exists.");
        }
        this.userData.put(userData.getPhoneNumber(),userData);
    }

    @Override
    public void remove(User data) {
        if (!userData.containsKey(data.getPhoneNumber())){
            throw new RuntimeException("No such User Exist");
        }
        userData.remove(data.getPhoneNumber());
    }
}
