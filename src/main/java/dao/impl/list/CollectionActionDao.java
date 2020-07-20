package dao.impl.list;

import dao.IActionDao;
import domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionActionDao implements IActionDao {
    private List<Action> actions = new ArrayList<>();

    @Override
    public List<Action> getAll() {
        return actions;
    }

    @Override
    public <T extends Action> List<T> getByType(ActionType type) {
        ArrayList<T> actionsOfType = new ArrayList<>();

        switch (type) {
            case MISSION:
                for (Action action : actions)
                    if (action instanceof MissionAction)
                        actionsOfType.add((T) action);
                break;
            case ADVENTURE:
                for (Action action : actions)
                    if (action instanceof AdventureAction)
                        actionsOfType.add((T) action);
                break;
            case REST:
                for (Action action : actions)
                    if (action instanceof RestAction)
                        actionsOfType.add((T) action);
                break;
            case DUEL:
                for (Action action : actions)
                    if (action instanceof DuelAction)
                        actionsOfType.add((T) action);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return actionsOfType;
    }

    @Override
    public Optional<Action> getById(int ID) {
        for (Action action : actions) {
            if (action.getID() == ID)
                return Optional.of(action);
        }
        return Optional.empty();
    }

    @Override
    public void update(Action action) {
        for (int i = 0; i < actions.size(); ++i) {
            if (actions.get(i).getID() == action.getID()) {
                actions.set(i, action);
                return;
            }
        }
    }

    @Override
    public void delete(Action action) {
        actions.remove(action);
    }

    @Override
    public void create(Action action) {
        if (action == null)
            throw new IllegalArgumentException("Player can't be null.");

        if (!actions.contains(action))
            actions.add(action);
    }
}
