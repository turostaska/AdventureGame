package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "typ")
@JsonPropertyOrder(value = { "ID" })
@JsonSubTypes({
        @JsonSubTypes.Type(value = UsableTool.class, name = "UT"),
        @JsonSubTypes.Type(value = NonUsableTool.class, name = "NUT")})
@NoArgsConstructor
public abstract class Tool extends Buyable {
    @Getter @Setter private int maxQuantity;

    public Tool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy);
        this.maxQuantity = maxQuantity;
    }

    public abstract void useUp(Character usedBy);

    @Override
    public void useInDuel(Character by, Character against) {
        if (!by.possessesTool(this))
            return;

        by.heal(getHealingAmount());
        against.takeDamage(getDamage());

        useUp(by);
    }
}
