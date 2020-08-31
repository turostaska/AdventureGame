package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
public class Player extends Character {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    @Getter
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

    @Transactional
    public void setActionQueue(List<ScheduledTask> actionQueue) {
        this.actionQueue = actionQueue;
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
        return action.playerAbleToTakeOnAction(this) && actionQueue.size() < MAX_NUMBER_OF_ACTIONS;
    }

    public void addToActionQueue(ScheduledTask scheduledTask) {
        actionQueue.add(scheduledTask);
    }

    @JsonIgnore
    public Optional<ScheduledTask> getNextScheduledTask() {
        if (!actionQueue.isEmpty())
            return Optional.of(actionQueue.get(0));

        return Optional.empty();
    }

    @JsonIgnore
    public long getTimeToFinishAllTasksInSeconds() {
        long totalTime = 0;
        for (ScheduledTask a : actionQueue) {
            totalTime += a.getTimeOfRunningInSeconds();
        }
        return totalTime;
    }

    public Optional<ScheduledTask> popScheduledActionFromQueue() {
        if (!actionQueue.isEmpty()) {
            return Optional.of(actionQueue.remove(0));
        }
        return Optional.empty();
    }

}
