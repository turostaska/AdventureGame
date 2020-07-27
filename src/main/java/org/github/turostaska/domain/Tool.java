package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typ")
@JsonPropertyOrder(value = { "ID" })
@JsonSubTypes({
        @JsonSubTypes.Type(value = UsableTool.class, name = "UT"),
        @JsonSubTypes.Type(value = NonUsableTool.class, name = "NUT")})
public abstract class Tool extends Buyable {
    private int maxQuantity;

    public Tool() {}

    public Tool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy);
        this.maxQuantity = maxQuantity;
    }

    public abstract void useUp(Character usedBy);

    public int getMaxQuantity() {
        return maxQuantity;
    }
}
