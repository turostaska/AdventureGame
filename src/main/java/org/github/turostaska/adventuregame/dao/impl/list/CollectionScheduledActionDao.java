package org.github.turostaska.adventuregame.dao.impl.list;

import org.github.turostaska.adventuregame.dao.IScheduledActionDao;
import org.github.turostaska.adventuregame.domain.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionScheduledActionDao implements IScheduledActionDao {
    private List<ScheduledTask> list = new ArrayList<>();

    @Override
    public List<ScheduledTask> getAll() {
        return list;
    }

    @Override
    public Optional<ScheduledTask> getById(Long ID) {
        for (ScheduledTask scheduledTask : list) {
            if (scheduledTask.getId().equals(ID))
                return Optional.of(scheduledTask);
        }
        return Optional.empty();
    }

    @Override
    public List<ScheduledTask> getByPlayerName(String playerName) {
        List<ScheduledTask> result = new ArrayList<>();

        for (ScheduledTask scheduledTask : list)
            if (scheduledTask.getPlayer().getName().equals(playerName))
                result.add(scheduledTask);

        return result;
    }

    @Override
    public void update(ScheduledTask scheduledTask) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getId().equals(scheduledTask.getId())) {
                list.set(i, scheduledTask);
                return;
            }
        }
    }

    @Override
    public void delete(ScheduledTask scheduledTask) {
        list.remove(scheduledTask);
    }

    @Override
    public void create(ScheduledTask scheduledTask) {
        if (scheduledTask == null)
            throw new IllegalArgumentException("The scheduled action can't be null.");

        if (!list.contains(scheduledTask))
            list.add(scheduledTask);
    }
}
