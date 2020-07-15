package domain;

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

    protected HashMap<Tool, Integer> tools = new HashMap<>();
    protected int strength;

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


}
