package org.github.turostaska.domain;

import org.github.turostaska.service.ICharacterService;

import javax.persistence.Entity;

@Entity
public class NPC extends Character {
    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }
}
