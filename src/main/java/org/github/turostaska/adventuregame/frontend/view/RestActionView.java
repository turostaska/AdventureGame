package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.domain.RestAction;
import org.github.turostaska.adventuregame.frontend.ui.MainUI;
import org.github.turostaska.adventuregame.service.IActionService;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringView(name = RestActionView.NAME)
@Slf4j
public class RestActionView extends VerticalLayout implements View {
    public static final String NAME = "rest_view";

    private final Grid<RestAction> restGrid = new Grid<>();
    private boolean takeActionColumnIsInit = false;

    @Autowired IActionService actionService;
    @Autowired ICharacterService characterService;
    @Autowired IScheduledTaskService taskService;

    @PostConstruct
    public void init() {
        setSizeFull();

        ListDataProvider<RestAction> provider = new ListDataProvider<>(actionService.getAllRestActions());
        restGrid.setDataProvider(provider);

        restGrid.removeAllColumns();

        restGrid.addColumn(action -> action.getCost() + " ryo")
                .setCaption("Cost")
                .setMinimumWidthFromContent(true);
        restGrid.addColumn(action -> Util.formatTime(action.getTimeToFinishInSeconds()))
                .setCaption("Duration")
                .setSortProperty("timeToFinishInSeconds")
                .setMinimumWidthFromContent(true);

        addComponent(restGrid);

        restGrid.setWidthFull();
        restGrid.setHeightFull();

        setExpandRatio(restGrid, 1f);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addTakeActionColumn();
    }

    private void addTakeActionColumn() {
        if (takeActionColumnIsInit || ((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            return;

        restGrid.addColumn(this::addTakeActionButton, new ComponentRenderer())
                .setCaption("Start").setSortable(false)
                .setMinimumWidthFromContent(true);
        takeActionColumnIsInit = true;
    }

    private final List<Button> actionButtonList = new ArrayList<>();


    private Button addTakeActionButton(RestAction action) {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player player = characterService.getPlayerById(playerId).orElseThrow();
        Button button = new Button("Start", event -> {
            Player playerAtPress = characterService.getPlayerById(playerId).orElseThrow();
            Optional<Player> result = taskService.tryToScheduleActionForPlayer(playerAtPress, action);
            if (result.isPresent())
                Notification.show("Action scheduled successfully.", Notification.Type.HUMANIZED_MESSAGE);
            else
                Notification.show("Scheduling failed.", Notification.Type.ERROR_MESSAGE);
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
