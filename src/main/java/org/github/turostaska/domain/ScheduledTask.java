package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ScheduledTask {
    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter protected LocalDateTime estimatedTimeOfFinishing;

    @OneToOne
    @Getter @Setter private Action action;

    @OneToOne
    @JsonBackReference
    @Getter @Setter
    protected Player player;

    public ScheduledTask(Action action, Player player, LocalDateTime estimatedTimeOfFinishing) {
        this.action = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
    }

    public void trigger() {
        if (getAction().carryOutAndGetIfSuccessful(player))
            getAction().takeEffect(player);

        player.popScheduledActionFromQueue();
    }

    public long getTimeOfRunningInSeconds() {
        return getAction().getTimeToFinishInSeconds();
    }
}
