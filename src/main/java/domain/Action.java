package domain;

public abstract class Action {
    protected long timeToFinishInSeconds;
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

    public abstract void takeEffect(Player player);

}
