package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.domain.ScheduledTask;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class TaskGridSheet extends Grid<ScheduledTask> {

    private List<ScheduledTask> tasks;

    public TaskGridSheet() {

        var provider = getDataProvider();

        removeAllColumns();

        addColumn(task -> tasks.indexOf(task) + 1)
                .setCaption("Nr.").setEditable(false)
                .setMinimumWidthFromContent(true);
        addColumn(this::getDescription)
                .setCaption("Description")
                .setMinimumWidthFromContent(true).setSortable(false);
        addColumn(task -> task.getEstimatedTimeOfFinishing()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .setCaption("Estimated time of finishing")
                .setMinimumWidthFromContent(true).setSortable(false);
    }

    private String getDescription(ScheduledTask task) {
        return task.getAction().description();
    }

    public void invalidate(Player player) {
        tasks = player.getActionQueue();
        setDataProvider(new ListDataProvider<>(player.getActionQueue()));
    }
}
