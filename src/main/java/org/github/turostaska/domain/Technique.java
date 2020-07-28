package org.github.turostaska.domain;

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

}
