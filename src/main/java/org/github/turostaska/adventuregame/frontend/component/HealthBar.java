package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import org.github.turostaska.adventuregame.domain.Character;

public class HealthBar extends HorizontalLayout {
    private final ProgressBar healthBar = new ProgressBar(1f);
    private final TextField healthField = new TextField("");

    public HealthBar() {
        addComponent(healthBar);
        addComponent(healthField);

        healthField.setEnabled(false);

        healthField.setStyleName("healthfield");
        healthBar.setStyleName("healthbar");

        setCaption("Health");

    }

    public void invalidate(Character character) {
        healthBar.setValue(((float) character.getCurrentHP()) / character.getMaxHP());
        healthField.setPlaceholder(character.getCurrentHP() + "/" + character.getMaxHP());
    }
}
