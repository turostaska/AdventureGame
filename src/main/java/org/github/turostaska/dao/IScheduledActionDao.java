package org.github.turostaska.dao;

import org.github.turostaska.domain.ScheduledTask;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to basic data structure operations. Contains behaviour specific to the data structure in order to
 * provide a unified interface to the org.github.turostaska.service layer.
 */
public interface IScheduledActionDao {
    /**
     * Returns a list of all actions scheduled in the database
     * @return The list of all scheduled actions
     */
    List<ScheduledTask> getAll();

    /**
     * Returns the scheduled action with the desired ID if it exists, otherwise returns an empty object
     * @param ID The desired user's ID
     * @return The scheduled action with the given ID if it exists, otherwise an empty object
     */
    Optional<ScheduledTask> getById(Long ID);

    /**
     * Returns a list of all actions scheduled by a specific player
     * @param playerName The name of the player
     * @return The list of all actions scheduled by the player
     */
    List<ScheduledTask> getByPlayerName(String playerName);

    /**
     * Overwrites an existing scheduled action with the same ID with the one given as parameter if it exists
     * @param scheduledTask The scheduled action to be overwritten with
     */
    void update(ScheduledTask scheduledTask);

    /**
     * Deletes the scheduled action from the database if it exists
     * @param scheduledTask The scheduled action to be deleted
     */
    void delete(ScheduledTask scheduledTask);

    /**
     * Creates a new scheduled action in the database if it's unique
     * @param scheduledTask The scheduled action to be created
     */
    void create(ScheduledTask scheduledTask);

}
