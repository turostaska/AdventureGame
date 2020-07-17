package domain;

public class RestAction extends Action {
    public RestAction(int ID, long timeToFinishInSeconds) {
        super(ID, timeToFinishInSeconds);
    }

    @Override
    public void takeEffect(Player player) {
        player.heal(player.getMaxHP());
        player.restoreMana(player.getMaxMana());
    }
}
