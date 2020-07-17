package domain;

public class MissionAction extends Action {
    private int baseReward;

    public MissionAction(int ID, long timeToFinishInSeconds, int baseReward) {
        super(ID, timeToFinishInSeconds);
        this.baseReward = baseReward;
    }

    public enum Difficulty { E, D, C, B, A, S };

    @Override
    public void takeEffect(Player player) {
        //TODO
    }
}
