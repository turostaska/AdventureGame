package domain;

import service.ICharacterService;

import java.time.LocalDateTime;

public class ScheduledAction implements Runnable {
    private int ID;
    private LocalDateTime estimatedTimeOfFinishing;
    private Action action;
    private Player player;
    private ICharacterService characterService;

    public ScheduledAction(int ID, Action action, Player player, LocalDateTime estimatedTimeOfFinishing, ICharacterService characterService) {
        this.ID = ID;
        this.action = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
        this.characterService = characterService;
    }

    public LocalDateTime getEstimatedTimeOfFinishing() {
        return estimatedTimeOfFinishing;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void run() {
        action.takeEffect(player);
        player.removeScheduledActionFromQueue(this);
        player.update(characterService);
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimeOfRunningInSeconds() {
        return action.getTimeToFinishInSeconds();
    }
}
