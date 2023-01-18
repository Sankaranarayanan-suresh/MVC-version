package com.dunzo.database.model;

import com.dunzo.core.model.job.Job;
import java.util.Collection;
import java.util.HashMap;

public class JobDatabase implements DatabaseFunctions<Job> {
    private static JobDatabase databaseInstance = null;
    public static JobDatabase getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new JobDatabase();
        }
        return databaseInstance;
    }
    private JobDatabase() {
    }
    private final HashMap<String, Job> jobData = new HashMap<>();
    public Collection<Job> getAll() {
        return jobData.values();
    }

    public void set(Job data) {
        if (this.jobData.containsKey(data.jobID)) {
            throw new RuntimeException("The data you are trying to update does not exists.");
        }
        this.jobData.put(data.jobID, data);
    }

    @Override
    public Job get(String key) {
        return jobData.get(key);
    }

    @Override
    public void update(Job data) {
        if (!jobData.containsKey(data.jobID)) {
            throw new RuntimeException("The data you are trying to update does not exists.");
        }
        this.jobData.put(data.jobID, data);
    }

    @Override
    public void remove(Job data) {
        if (!jobData.containsKey(data.jobID)){
            throw new RuntimeException("No such User Exist");
        }
        jobData.remove(data.jobID);
    }
}
