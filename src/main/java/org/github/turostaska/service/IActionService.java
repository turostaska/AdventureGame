package org.github.turostaska.service;

import org.github.turostaska.domain.*;

import java.util.List;
import java.util.Optional;

public interface IActionService {
    /**
     * Creates an action if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param action The action to be created or be overwritten with
     * @return The action added or updated
     */
    Action addOrUpdate(Action action);

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
    Optional<Action> getById(Long ID);

    /**
     * Returns a list of all actions that have the specified type
     * @return The list of actions of type
     */
    List<RestAction> getAllRestActions();

    /**
     * Returns a list of all actions that have the specified type
     * @return The list of actions of type
     */
    List<AdventureAction> getAllAdventureActions();

    /**
     * Returns a list of all actions that have the specified type
     * @return The list of actions of type
     */
    List<MissionAction> getAllMissionActions();

    /**
     * Returns a list of all actions that have the specified type
     * @return The list of actions of type
     */
    List<DuelAction> getAllDuelActions();

    /**
     * Returns the rest action that is free in order to have players be able to have a forced rest.
     * @return The rest action with no costs
     */
    RestAction getFreeRestAction();

    void deleteById(Long id);

    List<Action> getAllStatelessActions();

    Optional<Action> getStatelessActionById(Long id);

    Optional<DuelAction> getDuelActionById(Long duel_id);
}
