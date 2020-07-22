package org.github.turostaska.service;

import org.github.turostaska.domain.Technique;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
public interface ITechniqueService {
    /**
     * Creates a technique if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param technique The technique to be created or be overwritten with
     */
    void addOrUpdate(Technique technique);

    /**
     * Deletes the technique from the data structure if it exists
     * @param technique Technique to be deleted
     */
    void delete(Technique technique);

    /**
     * Returns the technique with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired technique's ID
     * @return The technique with the given ID if it exists, otherwise an empty object
     */
    Optional<Technique> getById(Long ID);

    /**
     * Returns the technique with the desired name if it exists, otherwise returns an empty object
     * @param name The desired technique's name
     * @return The technique with the given name if it exists, otherwise an empty object
     */
    Optional<Technique> getByName(String name);
}
