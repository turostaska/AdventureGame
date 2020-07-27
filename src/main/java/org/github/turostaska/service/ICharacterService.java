package org.github.turostaska.service;

import org.github.turostaska.domain.*;

import org.github.turostaska.domain.Character;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
public interface ICharacterService {
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
    Optional<Player> getPlayerById(Long ID);

    /**
     * Returns the player with the desired name if it exists, otherwise returns an empty object
     * @param name The desired player's name
     * @return The player with the given name if it exists, otherwise an empty object
     */
    Optional<Player> getPlayerByName(String name);

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


    /**
     * Creates a NPC if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param npc The NPC to be created or be overwritten with
     */
    void addOrUpdate(NPC npc);

    /**
     * Deletes the NPC from the data structure if it exists
     * @param npc NPC to be deleted
     */
    void delete(NPC npc);

    /**
     * Returns the NPC with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired NPC's ID
     * @return The NPC with the given ID if it exists, otherwise an empty object
     */
    Optional<NPC> getNPCById(Long ID);

    /**
     * Returns the NPC with the desired name if it exists, otherwise returns an empty object
     * @param name The desired NPC's name
     * @return The NPC with the given name if it exists, otherwise an empty object
     */
    Optional<NPC> getNPCByName(String name);

    /**
     * The tool is used by its user in a duel against the other character, but only if the user possesses that tool.
     * If the tool is usable, it will disappear from the user's inventory.
     * @param tool The tool to be used
     * @param usedBy The attacker that uses the tool
     * @param usedAgainst The defender against whom the attacker uses the tool
     */
    void useInDuel(Tool tool, Character usedBy, Character usedAgainst);

    /**
     * The technique is used by its user in a duel against the other character, but only if the user knows that
     * technique and he has enough mana.
     * @param technique The technique to be used
     * @param usedBy The character using the technique
     * @param usedAgainst The character against whom the technique is used
     */
    void useInDuel(Technique technique, Character usedBy, Character usedAgainst);

    /**
     * The specified NPC takes damage equal to the amount given. If his hit points fall below zero, it is set to 0
     * and if he's a player, he is sent to have a rest.
     * @param who The NPC taking damage
     * @param amount The amount of damage to take
     */
    void takeDamage(NPC who, int amount);

    /**
     * The specified player takes damage equal to the amount given. If his hit points fall below zero, it is set to 0
     * and if he's a player, he is sent to have a rest.
     * @param who The player taking damage
     * @param amount The amount of damage to take
     */
    void takeDamage(Player who, int amount);

    /**
     * The specified character restores hit points equal to the amount given. If it raises above the maximum value, it
     * is set to the cap.
     * @param who The character getting healed
     * @param amount The amount of hit points to heal
     */
    void heal(Character who, int amount);

    /**
     * Returns a list of all players
     * @return A list of all players
     */
    List<Player> getAllPlayers();

    /**
     * Returns a list of all NPCs
     * @return A list of all NPCs
     */
    List<NPC> getAllNPCs();

    void deletePlayerById(Long id);
}
