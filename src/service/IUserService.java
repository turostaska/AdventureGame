package service;

import domain.User;
import java.util.Optional;

public interface IUserService {
    /**
     * Creates a user if a record with the same ID doesn't exist, otherwise it overwrites it with the one given
     * @param user The user to be created or be overwritten with
     */
    void addOrUpdate(User user);

    /**
     * Deletes the user from the data structure if it exists
     * @param user User to be deleted
     */
    void delete(User user);

    /**
     * Returns the user with the desired ID if it exists in the data structure, otherwise returns an empty object
     * @param ID The desired user's ID
     * @return The user with the given ID if it exists, otherwise an empty object
     */
    Optional<User> getById(int ID);

    /**
     * Returns the user with the desired username if it exists, otherwise returns an empty object
     * @param name The desired user's username
     * @return The user with the given name if it exists, otherwise an empty object
     */
    Optional<User> getByName(String name);

    /**
     * Returns the user with the desired e-mail address if it exists, otherwise returns an empty object
     * @param email The desired user's e-mail address
     * @return The user with the given e-mail address if it exists, otherwise an empty object
     */
    Optional<User> getByEmail(String email);
}
