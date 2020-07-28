package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.Util;
import org.github.turostaska.service.ICharacterService;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@NoArgsConstructor
@JsonPropertyOrder(value = { "id", "inventoryKeys", "inventoryValues" }, alphabetic = true)
public abstract class Character {

    @Id @GeneratedValue
    @Getter @Setter
    protected Long id;

    @Getter @Setter protected String name;
    @Getter @Setter protected int currentHP;
    @Getter @Setter protected int maxHP;
    @Getter @Setter protected int currentMana;
    @Getter @Setter protected int maxMana;
    @Getter @Setter protected int strength;

    @ManyToMany
    @Getter @Setter
    protected List<Technique> knownTechniques = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="inventory")
    @MapKeyColumn(name="tool")
    @JsonIgnore
    @Getter @Setter
    protected Map<Tool, Integer> tools = new HashMap<>();

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

    public abstract void update(ICharacterService service);

    public boolean knowsTechnique(Technique technique) {
        return knownTechniques.contains(technique);
    }

    public boolean possessesTool(Tool tool) {
        return tools.containsKey(tool);
    }

    @JsonProperty("inventoryKeys")
    public Set<Tool> getInventoryKeys() {
        return tools.keySet();
    }

    @JsonProperty("inventoryKeys")
    public void setInventoryKeys(Set<Tool> keys) {
        tools.clear();
        keys.forEach(tool -> tools.put(tool, 0));
    }

    @JsonProperty("inventoryValues")
    public Collection<Integer> getInventoryValues() {
        return tools.values();
    }

    @JsonProperty("inventoryValues")
    public void setInventoryValues(Collection<Integer> values) {
        var arrayOfValues = new ArrayList<>(values);
        AtomicInteger i = new AtomicInteger();
        tools.keySet().forEach(key -> tools.replace(key, arrayOfValues.get(i.getAndIncrement())));
    }
}
