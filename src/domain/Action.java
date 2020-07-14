package domain;

public abstract class Action {
    protected long timeToFinishInSeconds;

    public abstract void takeEffect(Player player);

}
