package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name= "CHARPLAYER")
@Entity
@NoArgsConstructor
@JsonPropertyOrder(value = { "id", "inventoryKeys", "inventoryValues" }, alphabetic = true)
public abstract class Character {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDSEQ")
    @SequenceGenerator(name="IDSEQ", sequenceName="IDSEQ", allocationSize=1)
    @Getter @Setter
    protected Long id;

    @Getter @Setter protected String name;
    @Getter @Setter protected int currentHP;
    @Getter @Setter protected int maxHP;
    @Getter @Setter protected int currentMana;
    @Getter @Setter protected int maxMana;
    @Getter @Setter protected int strength;
    @Getter @Setter protected int money;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Getter @Setter
    protected List<Technique> knownTechniques = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="inventory")
    @MapKeyColumn(name="tool")
    @JsonIgnore
    @Getter @Setter
    protected Map<Tool, Integer> tools = new HashMap<>();

    public Character(String name, int maxHP, int maxMana, int strength, int money) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.strength = strength;
        this.money = money;
    }

    public void takeDamage(int damage) {
        this.currentHP = Util.clamp(currentHP - damage, 0, maxHP);
    }

    public void heal(int amount) {
        takeDamage(-amount);
    }

    public void loseMana(int amount) {
        this.currentMana = Util.clamp(currentMana - amount, 0, maxMana);
    }

    public void restoreMana(int amount) {
        loseMana(-amount);
    }

    @JsonIgnore
    public boolean isAlive() {
        return currentHP > 0;
    }

    public void useUpUsableTool(UsableTool tool) {
        tools.merge(tool, 1, (a, b) -> a - b);

        this.strength -= tool.getStrengthIncrease();

        if (tools.get(tool) == 0)
            tools.remove(tool);
    }

    public void addMoney(int sum) {}

    public void obtainTool(Tool tool) {
        tools.merge(tool, 1, (a, b) -> a + b);

        if (tools.get(tool) <= tool.getMaxQuantity())
            this.strength += tool.getStrengthIncrease();
    }

    public void learnTechnique(Technique technique) {
        knownTechniques.add(technique);
        this.strength += technique.getStrengthIncrease();
    }

    public abstract void update(ICharacterService service);

    public boolean knowsTechnique(Technique technique) {
        return knownTechniques.contains(technique);
    }

    public boolean possessesTool(Tool tool) {
        return tools.containsKey(tool);
    }

    @JsonProperty(value = "inventoryKeys", access = JsonProperty.Access.READ_ONLY)
    public ArrayList<Tool> getInventoryKeys() {
        return new ArrayList<>(tools.keySet());
    }

    @JsonProperty(value = "inventoryValues", access = JsonProperty.Access.READ_ONLY)
    @JsonPropertyOrder()
    public ArrayList<Integer> getInventoryValues() {
        return new ArrayList<>(tools.values());
    }
}
