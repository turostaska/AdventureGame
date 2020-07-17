package domain;

import service.ICharacterService;

public class NPC extends Character {
    @Override
    public void update(ICharacterService service) {
        service.addOrUpdate(this);
    }
}
