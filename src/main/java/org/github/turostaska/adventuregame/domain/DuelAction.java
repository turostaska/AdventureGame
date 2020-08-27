package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;

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

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        //todo: szofisztikáltabb harcrendszer? legalább sebzés kéne bele tbh
        int diceRoll = Util.getRandomInteger(0, who.getStrength() + opponent.getStrength() + 1);

        attackerWon = diceRoll <= who.getStrength();

        return true;
    }

    @Override
    public String description() {
        return "Duel against " + opponent.getName();
    }
}
