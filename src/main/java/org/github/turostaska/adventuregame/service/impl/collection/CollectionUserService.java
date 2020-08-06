package org.github.turostaska.adventuregame.service.impl.collection;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.validator.routines.EmailValidator;
import org.github.turostaska.adventuregame.dao.IUserDao;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.IUserService;

import java.util.List;
import java.util.Optional;

public class CollectionUserService implements IUserService {
    private IUserDao dao;

    public CollectionUserService(IUserDao dao) {
        this.dao = dao;
    }

    @Override
    public User addOrUpdate(User user) {
        if (dao.getById(user.getId()).isEmpty())
            dao.create(user);
        else
            dao.update(user);

        return getByName(user.getUserName()).orElseThrow();
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        getById(id).ifPresent(this::delete);
    }

    @Override
    public Optional<User> getById(Long ID) {
        return dao.getById(ID);
    }

    @Override
    public Optional<User> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return dao.getByEmail(email);
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
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPlayerForUser(User user) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    private User register(String name, String password, String email) {
        dao.create(new User(name, password, email));
        return getByName(name).orElseThrow();
    }

    private boolean usernameIsTaken(String username) {
        return dao.getByName(username).isPresent();
    }

    private boolean emailIsTaken(String email) {
        return dao.getByEmail(email).isPresent();
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
