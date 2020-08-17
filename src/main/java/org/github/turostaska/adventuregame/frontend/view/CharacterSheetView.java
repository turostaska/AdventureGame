package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = CharacterSheetView.NAME)
@Slf4j
public class CharacterSheetView extends FormLayout implements View {
    public static final String NAME = "character_sheet";
    private Character activeCharacter;

    private TextField nameField = new TextField("Name");
    private TextField moneyField = new TextField("Money");

    @Autowired ICharacterService characterService;
    //todo: eszközök meg technikák kiíratása valahogy idk
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Long passedId = Long.parseLong(event.getParameters());
        if (characterService.findById(passedId).isEmpty()) {
            getUI().getNavigator().navigateTo(LoginView.NAME);
            return;
        }

        activeCharacter = characterService.findById(passedId).get();
        invalidate();
    }

    @PostConstruct
    private void init() {
        addComponentsToForm();
    }

    private void addComponentsToForm() {
        nameField.setEnabled(false);
        moneyField.setEnabled(false);

        addComponents(nameField, moneyField);
    }

    private void invalidate() {
        nameField.setPlaceholder(activeCharacter.getName());
        moneyField.setPlaceholder(activeCharacter.getMoney() + " ryo");

    }

}
