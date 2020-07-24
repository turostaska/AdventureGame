package org.github.turostaska.domain;

import javax.persistence.Entity;

@Entity
public class Technique extends Buyable {
    private int costToCast;

    public Technique(String name, int damage, int heal, int costToBuy, int costToCast) {
        super(name, damage, heal, costToBuy);
        this.costToCast = costToCast;
    }

    public Technique() { }

    public int getCostToCast() {
        return costToCast;
    }

}
