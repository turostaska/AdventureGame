package domain;

import java.time.LocalDateTime;

public class ScheduledAction {
    private int ID;
    private LocalDateTime timeOfFinishing;
    private Action action;
    private Player player;

    public ScheduledAction(int ID, Action action, Player player, LocalDateTime timeOfFinishing) {
        this.ID = ID;
        this.action = action;
        this.player = player;
        this.timeOfFinishing = timeOfFinishing;
    }

    public void trigger() {
        action.takeEffect(player);
    }

    public LocalDateTime getTimeOfFinishing() {
        return timeOfFinishing;
    }
}
