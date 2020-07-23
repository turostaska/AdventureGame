package org.github.turostaska.domain;

import org.github.turostaska.service.ICharacterService;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ScheduledTask {
    @Id
    @GeneratedValue
    private Long ID;

    private LocalDateTime estimatedTimeOfFinishing;

    @OneToOne
    private Action action;

    @OneToOne
    private Player player;

    public Action getAction() {
        return action;
    }

    public ScheduledTask(Action action, Player player, LocalDateTime estimatedTimeOfFinishing) {
        this.action = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
    }

    public LocalDateTime getEstimatedTimeOfFinishing() {
        return estimatedTimeOfFinishing;
    }

    public Long getID() {
        return ID;
    }

    public void trigger() {
        action.takeEffect(player);
        player.removeScheduledActionFromQueue(this);
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimeOfRunningInSeconds() {
        return action.getTimeToFinishInSeconds();
    }
}
