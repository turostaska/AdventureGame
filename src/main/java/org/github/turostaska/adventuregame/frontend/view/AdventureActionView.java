package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.domain.AdventureAction;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.frontend.ui.MainUI;
import org.github.turostaska.adventuregame.service.IActionService;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = AdventureActionView.NAME)
@Slf4j
public class AdventureActionView extends VerticalLayout implements View {
    public static final String NAME = "adventure_view";

    private final Grid<AdventureAction> adventureGrid = new Grid<>();
    private boolean takeActionColumnIsInit = false;

    @Autowired IActionService actionService;
    @Autowired ICharacterService characterService;
    @Autowired IScheduledTaskService taskService;

    @PostConstruct
    public void init() {
        setSizeFull();

        ListDataProvider<AdventureAction> provider = new ListDataProvider<>(actionService.getAllAdventureActions());
        adventureGrid.setDataProvider(provider);

        adventureGrid.removeAllColumns();

        adventureGrid.addColumn(action -> Util.getRankFromDifficulty(action.getDifficulty()))
                .setCaption("Rank")
                .setMinimumWidthFromContent(true);
        adventureGrid.addColumn(action -> Util.formatTime(action.getTimeToFinishInSeconds()))
                .setCaption("Duration")
                .setSortProperty("timeToFinishInSeconds")
                .setMinimumWidthFromContent(true);
        adventureGrid.addColumn(this::stringOfRewards)
                .setCaption("Rewards")
                .setMinimumWidthFromContent(true);

        addComponent(adventureGrid);

        adventureGrid.setWidthFull();
        adventureGrid.setHeightFull();

        setExpandRatio(adventureGrid, 1f);
    }

    private String stringOfRewards(AdventureAction action) {
        String str = "";
        for (var technique : action.getTechniqueRewards())
            str = str.concat(technique.getName() + ", ");
        for (var tool : action.getToolRewards())
            str = str.concat(tool.getName() + ", ");
        return str.substring(0, str.length() - 2);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addTakeActionColumn();
    }

    private void addTakeActionColumn() {
        if (takeActionColumnIsInit || ((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            return;

        adventureGrid.addColumn(this::addTakeActionButton, new ComponentRenderer())
                .setCaption("Start").setSortable(false)
                .setMinimumWidthFromContent(true);
        takeActionColumnIsInit = true;
    }

    private final List<Button> actionButtonList = new ArrayList<>();


    private Button addTakeActionButton(AdventureAction action) {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player player = characterService.getPlayerById(playerId).orElseThrow();
        Button button = new Button("Start", event -> {
            Player playerAtPress = characterService.getPlayerById(playerId).orElseThrow();
            taskService.tryToScheduleActionForPlayer(playerAtPress, action);
        });
        button.addClickListener(event -> invalidateButtons());
        button.setEnabled(player.getActionQueue().size() < Player.MAX_NUMBER_OF_ACTIONS);
        actionButtonList.add(button);
        return button;
    }

    private void invalidateButtons() {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player purchaser = characterService.getPlayerById(playerId).orElseThrow();
        actionButtonList.forEach((button)
                -> button.setEnabled(purchaser.getActionQueue().size() < Player.MAX_NUMBER_OF_ACTIONS));
    }

}
