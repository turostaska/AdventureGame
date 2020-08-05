package org.github.turostaska.domain;

import lombok.NoArgsConstructor;
import org.github.turostaska.service.ICharacterService;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class NPC extends Character {
    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }

    public NPC(String name, int maxHP, int maxMana, int strength, int money) {
        super(name, maxHP, maxMana, strength, money);
    }
}
