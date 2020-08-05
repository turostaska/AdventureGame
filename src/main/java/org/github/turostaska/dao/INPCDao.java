package org.github.turostaska.dao;

import org.github.turostaska.domain.NPC;

import java.util.List;
import java.util.Optional;

public interface INPCDao {
    /**
     * Returns a list of all NPCs in the database
     * @return The list of all NPCs
     */
    List<NPC> getAll();

    /**
     * Returns the NPC with the desired ID if it exists, otherwise returns an empty object
     * @param ID The desired NPC's ID
     * @return The NPC with the given ID if it exists, otherwise an empty object
     */
    Optional<NPC> getById(Long ID);

    /**
     * Returns the NPC with the desired name if it exists, otherwise returns an empty object
     * @param name The desired NPC's name
     * @return The NPC with the given name if it exists, otherwise an empty object
     */
    Optional<NPC> getByName(String name);

    /**
     * Overwrites an existing NPC with the same ID with the one given as parameter if it exists
     * @param npc The NPC to be overwritten with
     */
    void update(NPC npc);

    /**
     * Deletes the NPC from the database if it exists
     * @param npc NPC to be deleted
     */
    void delete(NPC npc);

    /**
     * Creates a new NPC in the database if it's unique
     * @param npc NPC to be created
     */
    void create(NPC npc);
}
