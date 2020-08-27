package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.domain.Technique;
import org.github.turostaska.adventuregame.frontend.ui.MainUI;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.ITechniqueService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

//@UIScope
@SpringView(name = ScrollShopView.NAME)
@Slf4j
public class ScrollShopView extends VerticalLayout implements View {
    public static final String NAME = "scroll_shop";

    private final Grid<Technique> techniqueGrid = new Grid<>(Technique.class);

    @Autowired private ITechniqueService techniqueService;
    @Autowired private ICharacterService characterService;
    private boolean purchaseColumnIsInit = false;

    @PostConstruct
    private void init() {
        setSizeFull();

        setUpGrid();
    }

    ComboBox<String> knownComboBox = new ComboBox<>("Show only:");

    private void setUpGrid() {
        ListDataProvider<Technique> provider = new ListDataProvider<>(techniqueService.getAll());
        techniqueGrid.setDataProvider(provider);

        knownComboBox.setItems("Unknown");
        knownComboBox.addValueChangeListener(event -> applyFilter(provider));
        knownComboBox.setWidthUndefined();

        techniqueGrid.removeAllColumns();

        techniqueGrid.addColumn(Technique::getName)
                .setCaption("Name")
                .setMinimumWidthFromContent(true);
        techniqueGrid.addColumn(technique -> technique.getCostToBuy() + " ryo")
                .setCaption("Cost")
                .setStyleGenerator(technique -> "money")
                .setMinimumWidthFromContent(true);
        techniqueGrid.addColumn(technique -> technique.getDamage() == 0 ? null : technique.getDamage())
                .setCaption("Damage")
                .setStyleGenerator(technique -> technique.getDamage() >= 0 ? "damage" : "negative-damage")
                .setMinimumWidthFromContent(true);
        techniqueGrid.addColumn(technique -> technique.getHealingAmount() == 0 ? null : technique.getHealingAmount())
                .setCaption("Heal")
                .setStyleGenerator(technique -> technique.getHealingAmount() >= 0 ? "heal" : "self-harm")
                .setMinimumWidthFromContent(true);

        addPurchaseColumn();

        addComponents(knownComboBox, techniqueGrid);

        techniqueGrid.setHeightFull();
        techniqueGrid.setWidthFull();

        setExpandRatio(techniqueGrid, 0.9f);

    }

    private void addPurchaseColumn() {
        if (purchaseColumnIsInit || ((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            return;

        techniqueGrid.addColumn(this::addPurchaseButton, new ComponentRenderer())
                .setCaption("Purchase").setSortable(false)
                .setMinimumWidthFromContent(true);
        purchaseColumnIsInit = true;
    }

    private final Map<Technique, Button> techniqueButtonMap = new HashMap<>();

    private Button addPurchaseButton(Technique technique) {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player purchaser = characterService.getPlayerById(playerId).orElseThrow();
        Button button = new Button("Purchase", event -> {
            Player buyer = characterService.getPlayerById(playerId).orElseThrow();
            characterService.tryToLearnTechnique(buyer, technique);
            log.info("Purchase button pressed");
        });
        button.addClickListener(event -> invalidateButtons());
        button.setEnabled(purchaser.getMoney() >= technique.getCostToBuy() && !purchaser.getKnownTechniques().contains(technique));
        techniqueButtonMap.put(technique, button);
        return button;
    }

    private void invalidateButtons() {
        Long playerId = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
        Player purchaser = characterService.getPlayerById(playerId).orElseThrow();
        techniqueButtonMap.forEach((technique, button)
                -> button.setEnabled(purchaser.getMoney() >= technique.getCostToBuy()
                && !purchaser.getKnownTechniques().contains(technique)));
    }

    private void applyFilter(ListDataProvider<Technique> provider) {
        Player player;
        if (((MainUI) (UI.getCurrent())).getLoggedInUser() == null)
            player = null;
        else {
            Long id = ((MainUI) (UI.getCurrent())).getLoggedInUser().getPlayer().getId();
            player = characterService.getPlayerById(id).orElseThrow();
        }
        provider.clearFilters();
        if (knownComboBox.getValue() != null && player != null) {
            if (knownComboBox.getValue().equals("Unknown")) {
                provider.addFilter(technique -> !player.getKnownTechniques().contains(technique));
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addPurchaseColumn();
    }
}
