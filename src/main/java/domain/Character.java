package domain;

import service.ICharacterService;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Character {
    protected String name;
    protected int ID;
    protected int currentHP;
    protected int maxHP;
    protected int currentMana;
    protected int maxMana;
    protected ArrayList<Technique> knownTechniques = new ArrayList<>();
    protected HashMap<Tool, Integer> tools = new HashMap<>();
    protected int strength;

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
        this.currentHP = Math.max(0, currentHP - damage);
    }

    public void heal(int amount) {
        this.currentHP = Math.min(maxHP, currentHP + amount);
    }

    public void loseMana(int amount) {
        this.currentMana -= amount;
    }

    public void restoreMana(int amount) {
        this.currentMana = Math.min(maxMana, currentMana + amount);
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void useUpUsableTool(UsableTool tool) {
        tools.merge(tool, 1, (a, b) -> a - b);

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
