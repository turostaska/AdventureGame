package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class Action {
    @Getter @Setter protected long timeToFinishInSeconds;

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDSEQ")
    @SequenceGenerator(name="IDSEQ", sequenceName="IDSEQ", allocationSize=1)
    @Getter @Setter
    protected Long id;

    public static final long SECONDS = 1;
    public static final long MINUTES = 60 * SECONDS;
    public static final long HOURS   = 60 * MINUTES;
    public static final long DAYS    = 24 * HOURS;

    public Action(long timeToFinishInSeconds) {
        this.timeToFinishInSeconds = timeToFinishInSeconds;
    }

    //TO DO: ezek szerintem mind "üzleti logikának" minősülnek. Ezeket a funkciókat, valamilyen service-
    //      Minden ami több logikát tartalmaz mint settelés esetleg listához hozzáadás kivétel, az már service felelőségi körbe tartozik.
    public boolean playerAbleToTakeOnAction(Player player) {
        return true;
    }

    public abstract void takeEffect(Player player);

    public abstract boolean carryOutAndGetIfSuccessful(Player who);
}
