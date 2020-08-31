package org.github.turostaska.adventuregame.service;

import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.*;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
public interface IScheduledTaskService {
    /**
     * Creates a scheduled action if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param scheduledTask The scheduled action to be created or be overwritten with
     * @return The task added or updated
     */
    ScheduledTask addOrUpdate(ScheduledTask scheduledTask);

    /**
     * Deletes the scheduled action from the data structure if it exists
     * @param scheduledTask The scheduled action to be deleted
     */
    void delete(ScheduledTask scheduledTask);

    /**
     * Returns the scheduled action with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired user's ID
     * @return The scheduled action with the given ID if it exists, otherwise an empty object
     */
    Optional<ScheduledTask> getById(Long ID);

    List<ScheduledTask> getAll();

    /**
     * Returns a list of all actions scheduled by a specific player
     * @param player The player
     * @return The list of all actions scheduled by the player
     */
    List<ScheduledTask> getByPlayer(Player player);

    /**
     * Schedules the specified action for the player if he is eligible to do the task.
     * @param player The player that wants to schedule an action
     * @param action The action to be scheduled
     * @return The player if the scheduling was successful, an empty object otherwise
     */
    Optional<Player> tryToScheduleActionForPlayer(Player player, Action action);

    /**
     * Schedules the specified duel action for the player if he is eligible to do the task.
     * @param player The player that wants to schedule an action
     * @param action The duel action to be scheduled
     * @param opponent The character being challenged
     * @return The player if the scheduling was successful, an empty object otherwise
     */
    Optional<Player> tryToScheduleDuelActionForPlayer(Player player, DuelAction action, Character opponent);

    void deleteById(Long id);

    /**
     * Reschedules all the tasks that have been left unfinished when the server shut down.
     */
    void rescheduleUnfinishedTasksUponInitialization();

    void deleteAll();

    void trigger(ScheduledTask task);
}
