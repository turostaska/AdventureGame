package org.github.turostaska.adventuregame.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode
@Entity
@NoArgsConstructor
public class NonUsableTool extends Tool {
    public NonUsableTool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy, maxQuantity);
    }

    @Override
    public void useUp(Character usedBy) {}

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        NonUsableTool tool = (NonUsableTool) obj;
        return id.equals(tool.getId());
    }

}
