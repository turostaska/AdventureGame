package org.github.turostaska.service;

import org.github.turostaska.domain.User;
import java.util.Optional;

/**
 * Provides access to the data regardless of the data structure being used. Contains business logic.
 */
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

    /**
     * Tries to login with the given username and password
     * @param username The username given in the login prompt
     * @param password The password given in the login prompt
     * @return The user if the authentication was successful, an empty object otherwise
     */
    Optional<User> tryToLogIn(String username, String password);

    /**
     * Possible outcomes of an attempt to register a new user
     */
    enum RegistrationResult { SUCCESS, INVALID_EMAIL, INVALID_PASSWORD, INVALID_USERNAME, USERNAME_TAKEN, EMAIL_TAKEN };

    /**
     * Tries to register a new user
     * @param name      The username given in the login prompt
     * @param password  The password given in the login prompt
     * @param email     The e-mail address given in the login prompt
     * @return A code containing the result of the registration attempt
     */
    RegistrationResult tryToRegister(String name, String password, String email);
}
