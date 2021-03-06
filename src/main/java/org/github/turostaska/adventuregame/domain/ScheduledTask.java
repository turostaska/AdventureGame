package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class ScheduledTask {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDSEQ")
    @SequenceGenerator(name="IDSEQ", sequenceName="IDSEQ", allocationSize=1)
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

    public long getTimeOfRunningInSeconds() {
        return getAction().getTimeToFinishInSeconds();
    }

    public long timeLeftToFinish() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), getEstimatedTimeOfFinishing());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTask that = (ScheduledTask) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
