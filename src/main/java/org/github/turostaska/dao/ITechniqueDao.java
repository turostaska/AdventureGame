package org.github.turostaska.dao;

import org.github.turostaska.domain.Technique;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to basic data structure operations. Contains behaviour specific to the data structure in order to
 * provide a unified interface to the org.github.turostaska.service layer.
 */
public interface ITechniqueDao {
    /**
     * Returns a list of all techniques in the database
     * @return The list of all techniques
     */
    List<Technique> getAll();

    /**
     * Returns the technique with the desired ID if it exists, otherwise returns an empty object
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

    /**
     * Overwrites an existing technique with the same ID with the one given as parameter if it exists
     * @param technique The player to be overwritten with
     */
    void update(Technique technique);

    /**
     * Deletes the technique from the database if it exists
     * @param technique Technique to be deleted
     */
    void delete(Technique technique);

    /**
     * Creates a new technique in the database if it's unique
     * @param technique Technique to be created
     */
    void create(Technique technique);
}
