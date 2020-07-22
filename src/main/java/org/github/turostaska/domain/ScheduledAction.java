package org.github.turostaska.domain;

import org.github.turostaska.service.ICharacterService;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class ScheduledAction {
    @Id
    private int ID;

    private LocalDateTime estimatedTimeOfFinishing;

    @OneToOne
    private Action action;

    @OneToOne
    private Player player;

    //todo: ezt nagyon nem ide, a run meghívásakor a SAService kérje meg a CharacterService-t hogy frissítse
    //private ICharacterService characterService;

    public ScheduledAction(int ID, Action action, Player player, LocalDateTime estimatedTimeOfFinishing) {
        this.ID = ID;
        this.action = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
    }

    public LocalDateTime getEstimatedTimeOfFinishing() {
        return estimatedTimeOfFinishing;
    }

    public int getID() {
        return ID;
    }

    public void trigger() {
        action.takeEffect(player);
        player.removeScheduledActionFromQueue(this);
        // player.update(characterService);
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimeOfRunningInSeconds() {
        return action.getTimeToFinishInSeconds();
    }
}
