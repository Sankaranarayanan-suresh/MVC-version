package com.dunzo.core.model.job;

import java.util.Date;

public abstract class JobState {
    public final static class Assigned extends JobState{
        public final Date assignedDate;
        public Assigned(Date assignedDate) {
            this.assignedDate = assignedDate;
        }
    }

    public final static class PickedUP extends JobState {
        public final Date pickedUpDate;

        public PickedUP(Date pickedUpDate) {
            this.pickedUpDate = pickedUpDate;
        }
    }

    public final static class Delivered extends JobState {
        public final Date deliveredDate;
        public Delivered(Date deliveredDate) {
            this.deliveredDate = deliveredDate;
        }
    }

    public final static class NotDelivered extends JobState{
        public final Date notDeliveredDate;
        public final String reason;

        public NotDelivered(Date notDeliveredDate, String reason) {
            this.notDeliveredDate = notDeliveredDate;
            this.reason = reason;
        }
    }

    public final static class Cancelled extends JobState {
        public final Date cancelledDate;

        public Cancelled(Date cancelledDate) {
            this.cancelledDate = cancelledDate;
        }
    }
}
