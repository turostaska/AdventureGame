package org.github.turostaska.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ScheduledDuelTask extends ScheduledTask {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter
    private DuelAction duelAction;

    @Override
    public Action getAction() {
        return duelAction;
    }

    @Override
    public void setAction(Action action) {
        this.duelAction = (DuelAction)action;
    }

    public ScheduledDuelTask(DuelAction action, Player player, LocalDateTime estimatedTimeOfFinishing) {
        this.duelAction = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
    }
}
