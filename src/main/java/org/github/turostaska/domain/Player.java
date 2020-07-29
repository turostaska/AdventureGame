package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.service.ICharacterService;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
public class Player extends Character {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Getter @Setter
    private List<ScheduledTask> actionQueue = new ArrayList<>();

    @OneToOne
    @JsonBackReference
    @Getter @Setter
    private User user;

    public Player(User user) {
        this.name = user.getUserName();
        this.strength = 0;
        this.maxHP = BASE_HP;
        this.currentHP = maxHP;
        this.maxMana = BASE_MANA;
        this.currentMana = maxMana;
        this.money = BASE_MONEY;
        this.actionQueue = new ArrayList<>();
        this.user = user;
    }

    @Override
    public void addMoney(int sum) {
        this.money += sum;
    }

    public static final int BASE_HP = 100;
    public static final int BASE_MANA = 100;
    public static final int BASE_MONEY = 500;
    public static final int MAX_NUMBER_OF_ACTIONS = 5;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return this.id.equals(player.id);
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

    public void addToActionQueue(ScheduledTask scheduledTask) {
        actionQueue.add(scheduledTask);
    }

    @JsonIgnore
    public long getTimeToFinishAllTasksInSeconds() {
        long totalTime = 0;
        for (ScheduledTask a : actionQueue) {
            totalTime += a.getTimeOfRunningInSeconds();
        }
        return totalTime;
    }

    public void removeScheduledActionFromQueue(ScheduledTask scheduledTask) {
        actionQueue.remove(scheduledTask);
        scheduledTask.setPlayer(null);
    }

    public Optional<ScheduledTask> popScheduledActionFromQueue() {
        if (!actionQueue.isEmpty()) {
            var first = Optional.of(actionQueue.remove(0));
            return first;
        }
        return Optional.empty();
    }

    public void triggerNextTaskInQueue() {
        if (!actionQueue.isEmpty())
            actionQueue.get(0).trigger();
    }
}
