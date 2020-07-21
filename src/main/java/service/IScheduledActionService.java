package service;

import domain.Action;
import domain.Player;
import domain.ScheduledAction;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
public interface IScheduledActionService {
    /**
     * Creates a scheduled action if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param scheduledAction The scheduled action to be created or be overwritten with
     */
    void addOrUpdate(ScheduledAction scheduledAction);

    /**
     * Deletes the scheduled action from the data structure if it exists
     * @param scheduledAction The scheduled action to be deleted
     */
    void delete(ScheduledAction scheduledAction);

    /**
     * Returns the scheduled action with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired user's ID
     * @return The scheduled action with the given ID if it exists, otherwise an empty object
     */
    Optional<ScheduledAction> getById(int ID);

    /**
     * Returns a list of all actions scheduled by a specific player
     * @param playerName The name of the player
     * @return The list of all actions scheduled by the player
     */
    List<ScheduledAction> getByPlayerName(String playerName);

    /**
     * Schedules the specified action for the player if he is eligible to do the task.
     * @param player The player that wants to schedule an action
     * @param action The action to be scheduled
     */
    void tryToScheduleActionForPlayer(Player player, Action action);
}
