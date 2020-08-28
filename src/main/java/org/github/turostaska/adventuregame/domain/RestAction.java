package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.service.ICharacterService;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REST")
public class RestAction extends Action {
    @Getter @Setter private int cost;

    public RestAction(long timeToFinishInSeconds, int cost) {
        super(timeToFinishInSeconds);
        this.cost = cost;
    }

    @Override
    public void takeEffect(Player player) {
        player.heal(player.getMaxHP());
        player.restoreMana(player.getMaxMana());
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who, ICharacterService characterService) {
        return true;
    }

    @Override
    public String description() {
        return "Rest";
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
