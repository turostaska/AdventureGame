package org.github.turostaska.domain;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class Action {
    protected long timeToFinishInSeconds;

    @Id
    @GeneratedValue
    protected Long ID;

    public static final long SECONDS = 1;
    public static final long MINUTES = 60 * SECONDS;
    public static final long HOURS   = 60 * MINUTES;
    public static final long DAYS    = 24 * HOURS;

    public Action(long timeToFinishInSeconds) {
        this.timeToFinishInSeconds = timeToFinishInSeconds;
    }

    public long getTimeToFinishInSeconds() {
        return timeToFinishInSeconds;
    }

    public Long getID() {
        return ID;
    }

    public boolean playerAbleToTakeOnAction(Player player) {
        return true;
    }

    public abstract void takeEffect(Player player);

    public abstract boolean carryOutAndGetIfSuccessful(Player who);
}
