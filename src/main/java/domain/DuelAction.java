package domain;

public class DuelAction extends Action {
    private Player opponent;

    public DuelAction(int ID, long timeToFinishInSeconds, Player opponent) {
        super(ID, timeToFinishInSeconds);
        this.opponent = opponent;
    }

    @Override
    public void takeEffect(Player player) {
        //TODO
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        //todo
        return false;
    }
}
