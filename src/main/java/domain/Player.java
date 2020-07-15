package domain;

import java.util.Objects;
import java.util.Queue;

public class Player extends Character {
    private int money;
    private Queue<ScheduledAction> actionQueue;

    private User user;
    // for testing purposes
    public Player(String name) {
        this.name = name;
        this.ID = currentID++;
        this.strength = 0;
        this.maxHP = BASE_HP;
        this.currentHP = maxHP;
        this.maxMana = BASE_MANA;
        this.currentMana = maxMana;
        this.money = BASE_MONEY;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int sum) {
        this.money += sum;
    }

    private static int currentID = 1;

    protected static final int BASE_HP = 100;
    protected static final int BASE_MANA = 100;
    protected static final int BASE_MONEY = 500;
    protected static final int MAX_NUMBER_OF_ACTIONS = 5;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return this.ID == player.ID;
    }

}
