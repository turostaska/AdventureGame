package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class StatefulScheduledTask extends ScheduledTask {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter
    @JsonIgnore
    private Action statefulAction;
    //todo: StatefulAction class?

    @Override
    public Action getAction() {
        return statefulAction;
    }

    @Override
    public void setAction(Action action) {
        this.statefulAction = action;
    }

    public StatefulScheduledTask(DuelAction action, Player player, LocalDateTime estimatedTimeOfFinishing) {
        this.statefulAction = action;
        this.player = player;
        this.estimatedTimeOfFinishing = estimatedTimeOfFinishing;
    }
}
