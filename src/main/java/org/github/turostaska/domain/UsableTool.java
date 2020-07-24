package org.github.turostaska.domain;

import javax.persistence.Entity;

@Entity
public class UsableTool extends Tool {
    public UsableTool() {}

    public UsableTool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy, maxQuantity);
    }

    @Override
    public void useUp(Character usedBy) {
        usedBy.useUpUsableTool(this);
    }

}
