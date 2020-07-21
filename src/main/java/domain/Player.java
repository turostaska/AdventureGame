package domain;

import service.ICharacterService;

import java.time.LocalDateTime;
import java.util.*;

public class Player extends Character {
    private int money;
    private Deque<ScheduledAction> actionQueue;

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

    public Player(String name,
                  int ID,
                  int currentHP,
                  int maxHP,
                  int currentMana,
                  int maxMana,
                  ArrayList<Technique> knownTechniques,
                  HashMap<Tool, Integer> tools,
                  int strength,
                  int money,
                  Deque<ScheduledAction> actionQueue,
                  User user) {
        super(name, ID, currentHP, maxHP, currentMana, maxMana, knownTechniques, tools, strength);
        this.money = money;
        this.actionQueue = actionQueue;
        this.user = user;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int sum) {
        this.money += sum;
    }

    private static int currentID = 1;

    public static final int BASE_HP = 100;
    public static final int BASE_MANA = 100;
    public static final int BASE_MONEY = 500;
    public static final int MAX_NUMBER_OF_ACTIONS = 5;

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

    protected void obtainTool(Tool tool) {
        tools.merge(tool, 1, (a, b) -> a + b);

        if (tools.get(tool) <= tool.getMaxQuantity())
            this.strength += tool.getStrengthIncrease();
    }

    protected void learnTechnique(Technique technique) {
        knownTechniques.add(technique);
        this.strength += technique.getStrengthIncrease();
    }

    public boolean tryToLearnTechnique(Technique technique) {
        if (money < technique.getCostToBuy() || knownTechniques.contains(technique))
            return false;

        money -= technique.getCostToBuy();
        learnTechnique(technique);
        return true;
    }

    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }

    public void clearActionQueue() {
        actionQueue.clear();
    }

    public boolean ableToTakeOnAction(Action action) {
        return actionQueue.size() < MAX_NUMBER_OF_ACTIONS && action.playerAbleToTakeOnAction(this);
    }

    public void addToActionQueue(ScheduledAction scheduledAction) {
        actionQueue.add(scheduledAction);
    }

    public long getTimeToFinishAllTasksInSeconds() {
        long totalTime = 0;
        for (ScheduledAction a : actionQueue) {
            totalTime += a.getTimeOfRunningInSeconds();
        }
        return totalTime;
    }

    public Deque<ScheduledAction> getActionQueue() {
        return actionQueue;
    }

    public void removeScheduledActionFromQueue(ScheduledAction scheduledAction) {
        actionQueue.remove(scheduledAction);
    }
}
