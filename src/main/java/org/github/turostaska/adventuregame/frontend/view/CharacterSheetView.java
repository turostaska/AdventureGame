package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TabSheet;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.frontend.component.GeneralCharacterSheetLayout;
import org.github.turostaska.adventuregame.frontend.component.TaskGridSheet;
import org.github.turostaska.adventuregame.frontend.component.TechniqueGridSheet;
import org.github.turostaska.adventuregame.frontend.component.ToolGridSheet;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = CharacterSheetView.NAME)
@Slf4j
public class CharacterSheetView extends TabSheet implements View {
    public static final String NAME = "character_sheet";
    private Character activeCharacter;

    private final GeneralCharacterSheetLayout generalLayout = new GeneralCharacterSheetLayout();
    private final TechniqueGridSheet techniqueGrid = new TechniqueGridSheet();
    private final ToolGridSheet toolGrid = new ToolGridSheet();
    private TaskGridSheet taskSheet;

    @Autowired ICharacterService characterService;
    private boolean taskSheetIsInit = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Long passedId = Long.parseLong(event.getParameters());
        if (characterService.findById(passedId).isEmpty()) {
            getUI().getNavigator().navigateTo(LoginView.NAME);
            return;
        }

        activeCharacter = characterService.findById(passedId).get();
        if (activeCharacter instanceof Player && !taskSheetIsInit) {
            taskSheet = new TaskGridSheet();
            addTab(taskSheet, "Active tasks");
            taskSheetIsInit = true;
        }

        invalidate();
    }

    @PostConstruct
    private void init() {
        addTab(generalLayout, "General");
        addTab(techniqueGrid, "Techniques");
        addTab(toolGrid, "Tools");

    }

    private void invalidate() {
        generalLayout.invalidate(activeCharacter);
        techniqueGrid.invalidate(activeCharacter);
        toolGrid.invalidate(activeCharacter);
        if (activeCharacter instanceof Player)
            taskSheet.invalidate((Player)activeCharacter);
    }

}
