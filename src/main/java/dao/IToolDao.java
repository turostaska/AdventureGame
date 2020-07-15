package dao;

import domain.Tool;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to basic data structure operations. Contains behaviour specific to the data structure in order to
 * provide a unified interface to the service layer.
 */
public interface IToolDao {
    /**
     * Returns a list of all tools in the database
     * @return The list of all tools
     */
    List<Tool> getAll();

    /**
     * Returns the tool with the desired ID if it exists, otherwise returns an empty object
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

    /**
     * Overwrites an existing tool with the same ID with the one given as parameter if it exists
     * @param tool The tool to be overwritten with
     */
    void update(Tool tool);

    /**
     * Deletes the tool from the database if it exists
     * @param tool Tool to be deleted
     */
    void delete(Tool tool);

    /**
     * Creates a new tool in the database if it's unique
     * @param tool Tool to be created
     */
    void create(Tool tool);
}
