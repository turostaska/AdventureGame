package org.github.turostaska.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Buyable {
    protected String name;

    @Id
    @GeneratedValue
    protected Long ID;

    protected int damage;
    protected int healingAmount;
    protected int costToBuy;

    public String getName() {
        return name;
    }

    public Long getID() {
        return ID;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public int getCostToBuy() {
        return costToBuy;
    }

    public Buyable(String name, int damage, int heal, int costToBuy) {
        this.name = name;
        this.damage = damage;
        this.healingAmount = heal;
        this.costToBuy = costToBuy;
    }

    public int getStrengthIncrease() {
        return costToBuy / 100;
    }

}
