package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public abstract class Buyable {
    @Getter @Setter protected String name;

    @Id @GeneratedValue
    @Getter @Setter
    protected Long id;

    @Getter @Setter protected int damage;
    @Getter @Setter protected int healingAmount;
    @Getter @Setter protected int costToBuy;

    public Buyable(String name, int damage, int heal, int costToBuy) {
        this.name = name;
        this.damage = damage;
        this.healingAmount = heal;
        this.costToBuy = costToBuy;
    }

    @JsonIgnore
    public int getStrengthIncrease() {
        return costToBuy / 100;
    }

}
