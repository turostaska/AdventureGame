package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Technique extends Buyable {
    @Getter @Setter private int costToCast;

    public Technique(String name, int damage, int heal, int costToBuy, int costToCast) {
        super(name, damage, heal, costToBuy);
        this.costToCast = costToCast;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Technique tool = (Technique) obj;
        return id.equals(tool.getId());
    }

    @Override
    public void useInDuel(Character by, Character against) {
        if (by.getCurrentMana() < getCostToCast() || !by.knowsTechnique(this))
            return;

        by.heal(getHealingAmount());
        against.takeDamage(getDamage());
        by.loseMana(getCostToCast());
    }
}
