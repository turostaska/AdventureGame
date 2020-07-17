package domain;

import java.time.LocalDateTime;

public class ScheduledAction {
    private int ID;
    private LocalDateTime timeOfFinishing;
    private Action action;
    private Player player;

    public ScheduledAction(int ID, Action action, Player player) {
        this.ID = ID;
        this.action = action;
        this.player = player;
        timeOfFinishing = LocalDateTime.now().plusSeconds(action.getTimeToFinishInSeconds());
    }

    public void trigger() {
        action.takeEffect(player);
    }
}
