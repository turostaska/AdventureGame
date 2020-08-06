package org.github.turostaska.adventuregame.service.impl.repository;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.repository.IUserRepository;
import org.github.turostaska.adventuregame.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryUserService implements IUserService {
    @Autowired
    private IUserRepository repository;

    public RepositoryUserService() {}

    @Override
    public User addOrUpdate(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> getById(Long ID) {
        return repository.findById(ID);
    }

    @Override
    public Optional<User> getByName(String name) {
        return repository.findByUserName(name);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> tryToLogIn(String username, String password) {
        Optional<User> user = getByName(username);
        if (user.isEmpty())
            return Optional.empty();
        if (passwordsMatch(user.get(), password))
            return user;

        return Optional.empty();
    }

    private boolean passwordsMatch(User user, String givenPassword) {
        return user.getPassword().equals(givenPassword);
    }

    @Override
    public Optional<User> tryToRegister(String name, String password, String email) {
        if (canRegister(name, password, email))
            return Optional.of(register(name, password, email));

        return Optional.empty();
    }

    private boolean canRegister(String name, String password, String email) {
        return emailIsValid(email) && passwordIsValid(password) && usernameIsValid(name) && !emailIsTaken(email) &&
                !usernameIsTaken(name);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void createPlayerForUser(User user) {
        user.createPlayer();
        addOrUpdate(user);
    }

    private User register(String name, String password, String email) {
        return repository.save(new User(name, password, email));
    }

    private boolean usernameIsTaken(String username) {
        return getByName(username).isPresent();
    }

    private boolean emailIsTaken(String email) {
        return getByEmail(email).isPresent();
    }

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 16;

    private boolean usernameIsValid(String username) {
        String letter    = "(?=.*[a-zA-Z])";
        String noWhiteSpace = "(?=\\S+$)";
        String length    = ".{" + MIN_USERNAME_LENGTH + "," + MAX_USERNAME_LENGTH + "}";

        return username.matches( "^" + letter + noWhiteSpace + length + "$" );
    }

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;

    private boolean passwordIsValid(String password) {
        String uppercase = "(?=.*[A-Z])";
        String lowercase = "(?=.*[a-z])";
        String numeric   = "(?=.*[0-9])";
        String noWhiteSpace = "(?=\\S+$)";
        String length    = ".{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}";

        return password.matches( "^" + uppercase + lowercase + numeric + noWhiteSpace + length + "$" );
    }

    private boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}
