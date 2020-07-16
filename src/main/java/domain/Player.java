package domain;

import java.util.ArrayDeque;
import java.util.Queue;

public class Player extends Character {
    private int money;
    private Queue<ScheduledAction> actionQueue;

    private User user;

    public Player(User user) {
        this.name = user.getUserName();
        this.ID = currentID++;
        this.strength = 0;
        this.maxHP = BASE_HP;
        this.currentHP = maxHP;
        this.maxMana = BASE_MANA;
        this.currentMana = maxMana;
        this.money = BASE_MONEY;
        this.actionQueue = new ArrayDeque<>();
        this.user = user;
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

    public boolean tryToBuyTool(Tool tool) {
        if (money < tool.getCostToBuy())
            return false;

        money -= tool.getCostToBuy();
        obtainTool(tool);
        return true;
    }

    private void obtainTool(Tool tool) {
        tools.merge(tool, 1, (a, b) -> a + b);
    }

    public boolean tryToLearnTechnique(Technique technique) {
        if (money < technique.getCostToBuy() || knownTechniques.contains(technique))
            return false;

        money -= technique.getCostToBuy();
        knownTechniques.add(technique);
        return true;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (!isAlive())
            forceToHaveARest();
    }

    private void forceToHaveARest() {
        //TODO
    }
}
