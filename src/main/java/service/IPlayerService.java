package service;

import domain.Buyable;
import domain.Player;
import domain.Technique;
import domain.Tool;

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

    /**
     * Makes the player buy the desired tool if he is able to afford it
     * @param player The player that should buy the item
     * @param tool   The tool to be bought
     */
    void tryToBuyTool(Player player, Tool tool);


    /**
     * Makes the player learn the technique if he is able to afford it
     * @param player      The player that should learn the technique
     * @param technique   The tool to be learnt
     */
    void tryToLearnTechnique(Player player, Technique technique);
}
