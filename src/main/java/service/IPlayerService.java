package service;

import domain.Player;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
public interface IPlayerService {
    /**
     * Creates a player if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param player The player to be created or be overwritten with
     */
    void addOrUpdate(Player player);

    /**
     * Deletes the player from the data structure if it exists
     * @param player Player to be deleted
     */
    void delete(Player player);

    /**
     * Returns the player with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired player's ID
     * @return The player with the given ID if it exists, otherwise an empty object
     */
    Optional<Player> getById(int ID);

    /**
     * Returns the player with the desired name if it exists, otherwise returns an empty object
     * @param name The desired player's name
     * @return The player with the given name if it exists, otherwise an empty object
     */
    Optional<Player> getByName(String name);
}
