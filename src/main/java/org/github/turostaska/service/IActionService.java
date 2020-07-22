package org.github.turostaska.service;

import org.github.turostaska.dao.IActionDao;
import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.RestAction;

import java.util.List;
import java.util.Optional;

public interface IActionService {
    /**
     * Creates an action if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param action The action to be created or be overwritten with
     */
    void addOrUpdate(Action action);

    /**
     * Deletes the action from the data structure if it exists
     * @param action Action to be deleted
     */
    void delete(Action action);

    /**
     * Returns the action with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired action's ID
     * @return The action with the given ID if it exists, otherwise an empty object
     */
    Optional<Action> getById(int ID);

    /**
     * Returns a list of all actions that have the specified type
     * @param type The desired type of actions
     * @return The list of actions of type
     */
    <T extends Action> List<T> getByType(IActionDao.ActionType type);

    /**
     * Returns the rest action that is free in order to have players be able to have a forced rest.
     * @return The rest action with no costs
     */
    RestAction getFreeRestAction();
}
