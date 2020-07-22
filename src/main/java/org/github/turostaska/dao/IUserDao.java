package org.github.turostaska.dao;

import org.github.turostaska.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to basic data structure operations. Contains behaviour specific to the data structure in order to
 * provide a unified interface to the org.github.turostaska.service layer.
 */
public interface IUserDao {
    /**
     * Returns a list of all users in the database
     * @return The list of all users
     */
    List<User> getAll();

    /**
     * Returns the user with the desired ID if it exists, otherwise returns an empty object
     * @param ID The desired user's ID
     * @return The user with the given ID if it exists, otherwise an empty object
     */
    Optional<User> getById(int ID);

    /**
     * Returns the user with the desired name if it exists, otherwise returns an empty object
     * @param name The desired user's name
     * @return The user with the given name if it exists, otherwise an empty object
     */
    Optional<User> getByName(String name);

    /**
     * Returns the user with the desired e-mail address if it exists, otherwise returns an empty object
     * @param email The desired user's e-mail address
     * @return The user with the given e-mail address if it exists, otherwise an empty object
     */
    Optional<User> getByEmail(String email);

    /**
     * Overwrites an existing user with the same ID with the one given as parameter if it exists
     * @param user The user to be overwritten with
     */
    void update(User user);

    /**
     * Deletes the user from the database if it exists
     * @param user User to be deleted
     */
    void delete(User user);

    /**
     * Creates a new user in the database if it's unique
     * @param user User to be created
     */
    void create(User user);
}
