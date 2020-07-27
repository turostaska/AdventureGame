package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.*;
import org.github.turostaska.Util;
import org.github.turostaska.service.ICharacterService;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@JsonPropertyOrder(value = { "ID", "inventoryKeys", "inventoryValues" }, alphabetic = true)
public abstract class Character {
    protected String name;

    @Id
    @GeneratedValue
    protected Long ID;

    protected int currentHP;
    protected int maxHP;
    protected int currentMana;
    protected int maxMana;

    @ManyToMany
    protected List<Technique> knownTechniques = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="inventory")
    @MapKeyColumn(name="tool")
    @JsonIgnore
    protected Map<Tool, Integer> tools = new HashMap<>();

    protected int strength;

    protected Character(String name, int currentHP, int maxHP, int currentMana, int maxMana, ArrayList<Technique> knownTechniques, HashMap<Tool, Integer> tools, int strength) {
        this.name = name;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.knownTechniques = knownTechniques;
        this.tools = tools;
        this.strength = strength;
    }

    protected Character() {}

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return ID;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public Map<Tool, Integer> getTools() {
        return tools;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public List<Technique> getKnownTechniques() {
        return knownTechniques;
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

    public abstract void update(ICharacterService service);

    public boolean knowsTechnique(Technique technique) {
        return knownTechniques.contains(technique);
    }

    public boolean possessesTool(Tool tool) {
        return tools.containsKey(tool);
    }

    public void setId(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKnownTechniques(List<Technique> knownTechniques) {
        this.knownTechniques = knownTechniques;
    }

    public void setTools(Map<Tool, Integer> tools) {
        this.tools = tools;
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
