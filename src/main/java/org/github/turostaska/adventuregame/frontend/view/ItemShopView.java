package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.NonUsableTool;
import org.github.turostaska.adventuregame.domain.Tool;
import org.github.turostaska.adventuregame.domain.UsableTool;
import org.github.turostaska.adventuregame.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = ItemShopView.NAME)
@Slf4j
public class ItemShopView extends VerticalLayout implements View {
    public static final String NAME = "item_shop";

    private final Grid<Tool> toolGrid = new Grid<>(Tool.class);

    @Autowired IToolService toolService;

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

        addComponents(typeComboBox, toolGrid);

        toolGrid.setWidthUndefined();
        toolGrid.setHeightFull();

        setExpandRatio(toolGrid, 0.9f);
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
}
