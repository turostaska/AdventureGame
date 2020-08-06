package org.github.turostaska.adventuregame.dao;

import org.github.turostaska.adventuregame.domain.Action;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to basic data structure operations. Contains behaviour specific to the data structure in order to
 * provide a unified interface to the org.github.turostaska.adventuregame.service layer.
 */
public interface IActionDao {
    enum ActionType{ MISSION, DUEL, REST, ADVENTURE }

    /**
     * Returns a list of all actions in the database
     * @return The list of all actions
     */
    List<Action> getAll();

    /**
     * Returns a list of all actions that have the specified type
     * @param type The desired type of actions
     * @return The list of actions of type
     */
    <T extends Action> List<T> getByType(ActionType type);

    /**
     * Returns the action with the desired ID if it exists, otherwise returns an empty object
     * @param ID The desired action's ID
     * @return The action with the given ID if it exists, otherwise an empty object
     */
    Optional<Action> getById(Long ID);

    /**
     * Overwrites an existing action with the same ID with the one given as parameter if it exists
     * @param action The action to be overwritten with
     */
    void update(Action action);

    /**
     * Deletes the action from the database if it exists
     * @param action Action to be deleted
     */
    void delete(Action action);

    /**
     * Creates a new action in the database if it's unique
     * @param action Action to be created
     */
    void create(Action action);
}
