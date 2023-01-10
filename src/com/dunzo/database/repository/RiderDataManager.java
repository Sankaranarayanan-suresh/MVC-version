package com.dunzo.database.repository;

import com.dunzo.core.model.job.Job;
import com.dunzo.core.model.user.Rider;

public interface RiderDataManager {

    void changeRiderJob(Rider rider, Job job);

}
