package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.domain.ScheduledTask;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskGridSheet extends Grid<ScheduledTask> {

    AtomicInteger i = new AtomicInteger(1);

    public TaskGridSheet() {

        var provider = getDataProvider();

        removeAllColumns();

        addColumn(task -> i.getAndIncrement())
                .setCaption("Nr.").setEditable(false)
                .setMinimumWidthFromContent(true);
        addColumn(this::getDescription)
                .setCaption("Description")
                .setMinimumWidthFromContent(true);
        addColumn(task -> task.getEstimatedTimeOfFinishing()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .setCaption("Estimated time of finishing")
                .setMinimumWidthFromContent(true);
    }

    private String getDescription(ScheduledTask task) {
        return task.getAction().toString();
    }

    public void invalidate(Player player) {
        i.set(1);
        setDataProvider(new ListDataProvider<>(player.getActionQueue()));
    }
}
