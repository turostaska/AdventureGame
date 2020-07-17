package domain;

import java.util.List;

public class AdventureAction extends Action {
    public AdventureAction(int ID, long timeToFinishInSeconds, List<Buyable> rewards) {
        super(ID, timeToFinishInSeconds);
        this.rewards = rewards;
    }

    private List<Buyable> rewards;

    public List<Buyable> getRewards() {
        return rewards;
    }

    @Override
    public void takeEffect(Player player) {
        //TODO
    }
}
