package org.github.turostaska.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.Util;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@DiscriminatorValue("DUEL")
public class DuelAction extends Action {
    @OneToOne
    @Getter @Setter
    private Player opponent;
    @Getter @Setter private Boolean attackerWon;

    public DuelAction(long timeToFinishInSeconds, Player opponent) {
        super(timeToFinishInSeconds);
        this.opponent = opponent;
    }

    @Override
    public void takeEffect(Player player) {
        //todo: szofisztikáltabb nyeremény?
        Player winner, loser;
        if (attackerWon) {
            winner = player; loser = opponent;
        }
        else {
            winner = opponent; loser = player;
        }
        winner.addMoney(loser.getMoney() / 2);
        loser.addMoney( -( loser.getMoney() / 2 ) );
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        //todo: szofisztikáltabb harcrendszer? legalább sebzés kéne bele tbh
        int diceRoll = Util.getRandomInteger(0, who.getStrength() + opponent.getStrength() + 1);

        attackerWon = diceRoll <= who.getStrength();

        return true;
    }
}
