package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import org.github.turostaska.adventuregame.domain.Character;

public class ManaBar extends HorizontalLayout {
    private final ProgressBar manaBar = new ProgressBar(1f);
    private final TextField manaField = new TextField("");

    public ManaBar() {
        addComponent(manaBar);
        addComponent(manaField);

        manaField.setEnabled(false);

        manaField.setStyleName("healthfield");
        manaBar.setStyleName("manabar");

        setCaption("Mana");

    }

    public void invalidate(Character character) {
        manaBar.setValue(((float) character.getCurrentMana()) / character.getMaxMana());
        manaField.setPlaceholder(character.getCurrentMana() + "/" + character.getMaxMana());
    }
}
