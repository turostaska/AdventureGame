package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.frontend.ui.MainUI;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringView(name = ItemShopView.NAME)
@Slf4j
public class ItemShopView extends VerticalLayout implements View {
    public static final String NAME = "item_shop";

    private final Grid<Tool> toolGrid = new Grid<>(Tool.class);

    @Autowired IToolService toolService;
    @Autowired ICharacterService characterService;
    private boolean purchaseColumnIsInit = false;

    @PostConstruct
    private void init() {
        setSizeFull();

        setUpToolGrid();
    }

    ComboBox<String> typeComboBox = new ComboBox<>("Filter by type:");

    private void setUpToolGrid() {
        ListDataProvider<Tool> provider = new ListDataProvider<>(toolService.getAllTools());
        toolGrid.setDataProvider(provider);

        typeComboBox.setItems("Consumable", "Non-consumable");
        typeComboBox.addValueChangeListener(event -> applyFilter(provider));
        typeComboBox.setWidthUndefined();

        toolGrid.removeAllColumns();

        toolGrid.addColumn(Tool::getName)
                .setCaption("Name")
                .setMinimumWidthFromContent(true);
        toolGrid.addColumn(tool -> tool.getCostToBuy() + " ryo")
                .setCaption("Cost")
                .setStyleGenerator(tool -> "money")
                .setMinimumWidthFromContent(true);
        toolGrid.addColumn(tool -> tool.getDamage() == 0 ? null : tool.getDamage())
                .setCaption("Damage")
                .setStyleGenerator(item -> item.getDamage() >= 0 ? "damage" : "negative-damage")
                .setMinimumWidthFromContent(true);
        toolGrid.addColumn(tool -> tool.getHealingAmount() == 0 ? null : tool.getHealingAmount())
                .setCaption("Heal")
                .setStyleGenerator(item -> item.getHealingAmount() >= 0 ? "heal" : "self-harm")
                .setMinimumWidthFromContent(true);
        toolGrid.addColumn(tool -> tool instanceof UsableTool ? "consumable" : "non-consumable")
                .setCaption("Type");

        addPurchaseColumn();

        addComponents(typeComboBox, toolGrid);

        toolGrid.setHeightFull();
        toolGrid.setWidthFull();

        setExpandRatio(toolGrid, 0.9f);
    }

    private void addPurchaseColumn() {
        if (purchaseColumnIsInit || ((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            return;

        toolGrid.addColumn(this::addPurchaseButton, new ComponentRenderer())
                .setCaption("Purchase").setSortable(false)
                .setMinimumWidthFromContent(true);
        purchaseColumnIsInit = true;
    }

    private final Map<Tool, Button> toolButtonMap = new HashMap<>();

    private Button addPurchaseButton(Tool tool) {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player purchaser = characterService.getPlayerById(playerId).orElseThrow();
        Button button = new Button("Purchase", event -> {
            Player buyer = characterService.getPlayerById(playerId).orElseThrow();
            var result = characterService.tryToBuyTool(buyer, tool);
            if (result.isPresent())
                Notification.show("Purchase successful.", Notification.Type.HUMANIZED_MESSAGE);
            else
                Notification.show("Purchase unsuccessful.", Notification.Type.ERROR_MESSAGE);
        });
        button.addClickListener(event -> invalidateButtons());
        button.setEnabled(purchaser.getMoney() >= tool.getCostToBuy());
        toolButtonMap.put(tool, button);
        return button;
    }

    private void invalidateButtons() {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player purchaser = characterService.getPlayerById(playerId).orElseThrow();
        toolButtonMap.forEach((tool, button) -> button.setEnabled(purchaser.getMoney() >= tool.getCostToBuy()));
    }

    private void applyFilter(ListDataProvider<Tool> provider) {
        provider.clearFilters();
        if (typeComboBox.getValue() != null) {
            if (typeComboBox.getValue().equals("Consumable"))
                provider.addFilter(tool -> tool instanceof UsableTool);
            else
                provider.addFilter(tool -> tool instanceof NonUsableTool);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addPurchaseColumn();

        User user = ((MainUI) (UI.getCurrent())).getLoggedInUser();
        if (user != null && purchaseColumnIsInit) {
            invalidateButtons();
        }
    }
}
