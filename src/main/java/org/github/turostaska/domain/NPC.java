package org.github.turostaska.domain;

import org.github.turostaska.service.ICharacterService;

public class NPC extends Character {
    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }
}
