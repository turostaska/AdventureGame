package service.impl;

import dao.IUserDao;
import domain.User;
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
    public boolean tryToLogIn(String username, String password) {
        Optional<User> user = getByName(username);
        if (user.isEmpty())
            return false;
        if (passwordsMatch(user.get(), password))
            return true;

        return false;
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

    }

    private boolean usernameIsTaken(String username) {
        return true;
    }

    private boolean emailIsTaken(String email) {
        return true;
    }

    private boolean usernameIsValid(String username) {
        return true;
    }

    private boolean passwordIsValid(String password) {
        return true;
    }

    private boolean emailIsValid(String email) {
        return email.matches("/\\S+@\\S+\\.\\S+/");
    }
}
