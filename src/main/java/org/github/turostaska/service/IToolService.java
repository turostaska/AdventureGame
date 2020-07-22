package org.github.turostaska.service;

import org.github.turostaska.domain.Tool;

import java.util.Optional;

public interface IToolService {
    /**
     * Creates a tool if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param tool The tool to be created or be overwritten with
     */
    void addOrUpdate(Tool tool);

    /**
     * Deletes the tool from the data structure if it exists
     * @param tool Tool to be deleted
     */
    void delete(Tool tool);

    /**
     * Returns the tool with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired tool's ID
     * @return The tool with the given ID if it exists, otherwise an empty object
     */
    Optional<Tool> getById(int ID);

    /**
     * Returns the tool with the desired name if it exists, otherwise returns an empty object
     * @param name The desired tool's name
     * @return The tool with the given name if it exists, otherwise an empty object
     */
    Optional<Tool> getByName(String name);
}
