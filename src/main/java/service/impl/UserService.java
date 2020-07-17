package service.impl;

import dao.IUserDao;
import domain.User;
import org.apache.commons.validator.routines.EmailValidator;
import service.IUserService;

import java.util.Optional;

public class UserService implements IUserService {
    private IUserDao dao;

    public UserService(IUserDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(User user) {
        if (dao.getById(user.getID()).isEmpty())
            dao.create(user);
        else
            dao.update(user);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }

    @Override
    public Optional<User> getById(int ID) {
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
    public RegistrationResult tryToRegister(String name, String password, String email) {
        if ( !emailIsValid(email) )
            return RegistrationResult.INVALID_EMAIL;
        if ( !passwordIsValid(password) )
            return RegistrationResult.INVALID_PASSWORD;
        if ( !usernameIsValid(name) )
            return RegistrationResult.INVALID_USERNAME;
        if ( emailIsTaken(email) )
            return RegistrationResult.EMAIL_TAKEN;
        if ( usernameIsTaken(name) )
            return RegistrationResult.USERNAME_TAKEN;

        register(name, password, email);
        return RegistrationResult.SUCCESS;
    }

    private void register(String name, String password, String email) {
        dao.create(new User(name, password, email));
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