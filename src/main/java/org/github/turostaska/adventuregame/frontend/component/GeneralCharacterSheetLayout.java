package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.github.turostaska.adventuregame.domain.Character;

public class GeneralCharacterSheetLayout extends FormLayout {
    private final TextField nameField = new TextField("Name");
    private final TextField moneyField = new TextField("Money");
    private final HealthBar healthBar = new HealthBar();
    private final ManaBar manaBar = new ManaBar();

    public GeneralCharacterSheetLayout() {
        nameField.setEnabled(false);
        moneyField.setEnabled(false);

        addComponent(nameField);
        addComponent(healthBar);
        addComponent(manaBar);
        addComponent(moneyField);
    }

    public void invalidate(Character character) {
        nameField.setPlaceholder(character.getName());
        moneyField.setPlaceholder(character.getMoney() + " ryo");

        healthBar.invalidate(character);
        manaBar.invalidate(character);
    }
}
