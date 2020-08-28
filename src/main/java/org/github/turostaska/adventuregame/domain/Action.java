package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.service.ICharacterService;

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

    public boolean playerAbleToTakeOnAction(Player player) {
        return true;
    }

    public abstract void takeEffect(Player player);

    public abstract boolean carryOutAndGetIfSuccessful(Player who, ICharacterService characterService);

    public abstract String description();
}
