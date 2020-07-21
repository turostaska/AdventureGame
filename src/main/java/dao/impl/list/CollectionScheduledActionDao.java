package dao.impl.list;

import dao.IScheduledActionDao;
import domain.ScheduledAction;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionScheduledActionDao implements IScheduledActionDao {
    private List<ScheduledAction> list = new ArrayList<>();

    @Override
    public List<ScheduledAction> getAll() {
        return list;
    }

    @Override
    public Optional<ScheduledAction> getById(int ID) {
        for (ScheduledAction scheduledAction : list) {
            if (scheduledAction.getID() == ID)
                return Optional.of(scheduledAction);
        }
        return Optional.empty();
    }

    @Override
    public List<ScheduledAction> getByPlayerName(String playerName) {
        List<ScheduledAction> result = new ArrayList<>();

        for (ScheduledAction scheduledAction : list)
            if (scheduledAction.getPlayer().getName().equals(playerName))
                result.add(scheduledAction);

        return result;
    }

    @Override
    public void update(ScheduledAction scheduledAction) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getID() == scheduledAction.getID()) {
                list.set(i, scheduledAction);
                return;
            }
        }
    }

    @Override
    public void delete(ScheduledAction scheduledAction) {
        list.remove(scheduledAction);
    }

    @Override
    public void create(ScheduledAction scheduledAction) {
        if (scheduledAction == null)
            throw new IllegalArgumentException("The scheduled action can't be null.");

        if (!list.contains(scheduledAction))
            list.add(scheduledAction);
    }
}
