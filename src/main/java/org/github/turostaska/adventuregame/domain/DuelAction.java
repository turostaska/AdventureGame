package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.service.ICharacterService;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@DiscriminatorValue("DUEL")
public class DuelAction extends Action {

    @OneToOne
    @Getter @Setter
    private Character opponent;

    @Getter @Setter
    @JsonIgnore
    private Boolean attackerWon;

    public DuelAction(long timeToFinishInSeconds, Character opponent) {
        super(timeToFinishInSeconds);
        this.opponent = opponent;
    }

    @Override
    public void takeEffect(Player player) {
        Character winner, loser;
        if (attackerWon) {
            winner = player; loser = opponent;
        }
        else {
            winner = opponent; loser = player;
        }
        float ratio = Util.clamp((((float)loser.getStrength() + 1.0f)/ (winner.getStrength() + 1.0f)) - 0.5f, 0.1f, 1.0f);
        int reward = (int)(loser.getMoney() * ratio) / 100 * 100;
        winner.addMoney(reward);
        loser.addMoney(-reward);
    }

    private static final int MAX_NUM_OF_TURNS = 5;

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who, ICharacterService characterService) {
        for (int i = 0; i < MAX_NUM_OF_TURNS; ++i) {
            who.takeTurnInDuel(opponent);
            if (!opponent.isAlive()) {
                attackerWon = true;
                characterService.kill(opponent);
                break;
            }

            opponent.takeTurnInDuel(who);
            if (!who.isAlive()) {
                attackerWon = false;
                characterService.kill(who);
                break;
            }
        }

        if (attackerWon == null)
            attackerWon = who.getCurrentHP() > opponent.getCurrentHP();

        opponent.afterDuel();

        return true;
    }

    @Override
    public String description() {
        return "Duel against " + opponent.getName();
    }
}
