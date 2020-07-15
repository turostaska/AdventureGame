package dao;

import domain.Player;

import java.util.List;
import java.util.Optional;

public interface IPlayerDao {
    /**
     * Returns a list of all players in the database
     * @return The list of all players
     */
    List<Player> getAll();

    /**
     * Returns the player with the desired ID if it exists, otherwise returns an empty object
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

    /**
     * Overwrites an existing player with the same ID with the one given as parameter if it exists
     * @param player The player to be overwritten with
     */
    void update(Player player);

    /**
     * Deletes the player from the database if it exists
     * @param player Player to be deleted
     */
    void delete(Player player);

    /**
     * Creates a new player in the database if it's unique
     * @param player Player to be created
     */
    void create(Player player);
}
