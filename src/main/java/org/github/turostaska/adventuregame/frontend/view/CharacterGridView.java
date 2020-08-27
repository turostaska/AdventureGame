package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.DuelAction;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.frontend.ui.MainUI;
import org.github.turostaska.adventuregame.service.IActionService;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringView(name = CharacterGridView.NAME)
@Slf4j
public class CharacterGridView extends VerticalLayout implements View {
    public static final String NAME = "character-grid";

    private final Grid<Character> characterGrid = new Grid<>(Character.class);
    @Autowired private ICharacterService characterService;
    @Autowired private IScheduledTaskService taskService;
    @Autowired private IActionService actionService;

    private boolean challengeColumnIsInit = false;

    @PostConstruct
    private void init() {
        setSizeFull();

        setUpGrid();
    }

    private void setUpGrid() {
        ListDataProvider<Character> provider = new ListDataProvider<>(characterService.getAll());
        characterGrid.setDataProvider(provider);

        characterGrid.removeAllColumns();

        characterGrid.addColumn(character -> character instanceof Player ? "Player" : "NPC")
                .setCaption("Type")
                .setMinimumWidthFromContent(true);
        characterGrid.addColumn(Character::getName)
                .setCaption("Name")
                .setMinimumWidthFromContent(true);
        characterGrid.addColumn(Character::getCurrentHP)
                .setCaption("Health")
                .setMinimumWidthFromContent(true);
        characterGrid.addColumn(Character::getCurrentMana)
                .setCaption("Mana")
                .setMinimumWidthFromContent(true);
        characterGrid.addColumn(character -> character.getMoney() + " ryo")
                .setCaption("Money")
                .setMinimumWidthFromContent(true);

        characterGrid.addColumn(this::addGoToProfileButton, new ComponentRenderer())
                .setCaption("Profile").setSortable(false)
                .setMinimumWidthFromContent(true);

        addChallengeColumn();

        addComponent(characterGrid);

        characterGrid.setWidthFull(); characterGrid.setHeightFull();
        setExpandRatio(characterGrid, 1f);

    }

    private Button addGoToProfileButton(Character character) {
        return new Button("Go to profile", event ->
                getUI().getNavigator().navigateTo(CharacterSheetView.NAME + "/" + character.getId()));
    }

    private final Map<Character, Button> buttons = new HashMap<>();

    private void addChallengeColumn() {
        if (challengeColumnIsInit || ((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            return;

        characterGrid.addColumn(this::addChallengeButton, new ComponentRenderer())
                .setCaption("Challenge on duel").setSortable(false)
                .setMinimumWidthFromContent(true);

        challengeColumnIsInit = true;
    }

    private Button addChallengeButton(Character challenged) {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player challenger = characterService.getPlayerById(playerId).orElseThrow();
        Button button = new Button("Duel", event -> {
            Player attacker = characterService.getPlayerById(playerId).orElseThrow();
            Character attacked = characterService.findById(challenged.getId()).orElseThrow();

            //ez csak egy duellal működik
            DuelAction duelAction = actionService.getAllDuelActions().get(0);

            taskService.tryToScheduleDuelActionForPlayer(attacker, duelAction, attacked);
        });
        button.addClickListener(event -> invalidateButtons());
        button.setEnabled(challenger.getActionQueue().size() < Player.MAX_NUMBER_OF_ACTIONS && !challenged.equals(challenger));
        buttons.put(challenged, button);
        return button;
    }

    private void invalidateButtons() {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player challenger = characterService.getPlayerById(playerId).orElseThrow();
        buttons.forEach((challenged, button)
                -> button.setEnabled(challenger.getActionQueue().size() < Player.MAX_NUMBER_OF_ACTIONS && !challenged.equals(challenger)));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //ez így most nem frissíti a listát minden betöltésnél, de ig ez így rendjén van
        addChallengeColumn();
    }
}
