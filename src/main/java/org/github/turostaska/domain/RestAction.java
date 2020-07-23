package org.github.turostaska.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REST")
public class RestAction extends Action {
    public RestAction(long timeToFinishInSeconds, int cost) {
        super(timeToFinishInSeconds);
        this.cost = cost;
    }

    private int cost;

    @Override
    public void takeEffect(Player player) {
        player.heal(player.getMaxHP());
        player.restoreMana(player.getMaxMana());
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        return true;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean playerAbleToTakeOnAction(Player player) {
        if (player.getMoney() >= cost) {
            player.addMoney(-cost);
            return true;
        }
        return false;
    }
}
