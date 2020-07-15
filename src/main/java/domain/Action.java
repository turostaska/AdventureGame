package domain;

public abstract class Action {
    protected long timeToFinishInSeconds;
    protected int ID;

    public abstract void takeEffect(Player player);

}
