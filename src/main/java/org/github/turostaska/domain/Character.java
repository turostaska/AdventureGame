package org.github.turostaska.domain;

import org.github.turostaska.service.ICharacterService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public abstract class Character {
    protected String name;

    @Id
    @GeneratedValue
    protected int ID;

    protected int currentHP;
    protected int maxHP;
    protected int currentMana;
    protected int maxMana;
    protected ArrayList<Technique> knownTechniques = new ArrayList<>();
    protected HashMap<Tool, Integer> tools = new HashMap<>();
    protected int strength;

    protected Character(String name, int ID, int currentHP, int maxHP, int currentMana, int maxMana, ArrayList<Technique> knownTechniques, HashMap<Tool, Integer> tools, int strength) {
        this.name = name;
        this.ID = ID;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.knownTechniques = knownTechniques;
        this.tools = tools;
        this.strength = strength;
    }

    protected Character() {
    }

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

    public int getID() {
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

    public HashMap<Tool, Integer> getTools() {
        return tools;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public ArrayList<Technique> getKnownTechniques() {
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
}
