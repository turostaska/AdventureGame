package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public abstract class Buyable {
    @Getter @Setter protected String name;

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="IDSEQ")
    @SequenceGenerator(name="IDSEQ", sequenceName="IDSEQ", allocationSize=1)
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

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long temp1 = this.damage;
        result = (result*PRIME) + (this.name == null ? 43 : this.name.hashCode());
        result = (result*PRIME) + (int)(temp1 ^ (temp1 >>> 32));
        return result;
    }

    public abstract void useInDuel(Character by, Character against);
}
