package org.github.turostaska.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Action {
    protected long timeToFinishInSeconds;

    @Id
    @GeneratedValue
    protected int ID;

    public static final long SECONDS = 1;
    public static final long MINUTES = 60 * SECONDS;
    public static final long HOURS   = 60 * MINUTES;
    public static final long DAYS    = 24 * HOURS;

    public Action(int ID, long timeToFinishInSeconds) {
        this.timeToFinishInSeconds = timeToFinishInSeconds;
        this.ID = ID;
    }

    public long getTimeToFinishInSeconds() {
        return timeToFinishInSeconds;
    }

    public int getID() {
        return ID;
    }

    public boolean playerAbleToTakeOnAction(Player player) {
        return true;
    }

    public abstract void takeEffect(Player player);

    public abstract boolean carryOutAndGetIfSuccessful(Player who);
}
