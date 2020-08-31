package org.github.turostaska.adventuregame.domain;

import lombok.NoArgsConstructor;
import org.github.turostaska.adventuregame.service.ICharacterService;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class NPC extends Character {
    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }

    @Override
    public void afterDuel() {
        this.currentHP = this.maxHP;
        this.currentMana = this.maxMana;
    }

    public NPC(String name, int maxHP, int maxMana, int strength, int money) {
        super(name, maxHP, maxMana, strength, money);
    }
}
