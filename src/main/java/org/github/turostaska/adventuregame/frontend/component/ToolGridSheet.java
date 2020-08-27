package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.Tool;
import org.github.turostaska.adventuregame.domain.UsableTool;

import java.util.Map;

public class ToolGridSheet extends Grid<Tool> {

    private Map<Tool, Integer> toolMap;

    public ToolGridSheet() {
        removeAllColumns();

        addColumn(Tool::getName)
                .setCaption("Name")
                .setMinimumWidthFromContent(true);
        addColumn(tool -> tool.getDamage() == 0 ? null : tool.getDamage())
                .setCaption("Damage")
                .setStyleGenerator(item -> item.getDamage() >= 0 ? "damage" : "negative-damage")
                .setMinimumWidthFromContent(true);
        addColumn(tool -> tool.getHealingAmount() == 0 ? null : tool.getHealingAmount())
                .setCaption("Heal")
                .setStyleGenerator(item -> item.getHealingAmount() >= 0 ? "heal" : "self-harm")
                .setMinimumWidthFromContent(true);
        addColumn(tool -> tool instanceof UsableTool ? "consumable" : "non-consumable")
                .setCaption("Type");
        addColumn(tool -> toolMap.get(tool))
                .setCaption("Amount");
    }

    public void invalidate(Character character) {
        setDataProvider(new ListDataProvider<>(character.getTools().keySet()));
        toolMap = character.getTools();
    }
}
