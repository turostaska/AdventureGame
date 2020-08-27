package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.Technique;

public class TechniqueGridSheet extends Grid<Technique> {

    public TechniqueGridSheet() {
        removeAllColumns();

        addColumn(Technique::getName)
                .setCaption("Name")
                .setMinimumWidthFromContent(true);
        addColumn(technique -> technique.getDamage() == 0 ? null : technique.getDamage())
                .setCaption("Damage")
                .setStyleGenerator(technique -> technique.getDamage() >= 0 ? "damage" : "negative-damage")
                .setMinimumWidthFromContent(true);
        addColumn(technique -> technique.getHealingAmount() == 0 ? null : technique.getHealingAmount())
                .setCaption("Heal")
                .setStyleGenerator(technique -> technique.getHealingAmount() >= 0 ? "heal" : "self-harm")
                .setMinimumWidthFromContent(true);
    }

    public void invalidate(Character character) {
        setDataProvider(new ListDataProvider<>(character.getKnownTechniques()));
    }
}
