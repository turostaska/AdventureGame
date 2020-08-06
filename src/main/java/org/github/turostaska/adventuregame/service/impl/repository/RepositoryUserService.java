package org.github.turostaska.adventuregame.service.impl.repository;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.repository.IUserRepository;
import org.github.turostaska.adventuregame.service.IUserService;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class RepositoryUserService implements IUserService {
    @Autowired
    private IUserRepository repository;

    public RepositoryUserService() {}

    private static final PasswordValidator passwordValidator = new PasswordValidator(
            new LengthRule(8, 30),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
            new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
            new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
            new WhitespaceRule()
    );

    @Autowired private PasswordEncoder passwordEncoder;

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
    public User tryToRegister(String name, String password, String email) throws Exception {
        if ( !emailIsValid(email) )
            throw new Exception("Email address is not valid.");
        if ( !usernameIsValid(name) )
            throw new Exception("Username is not valid.");
        if ( emailIsTaken(email) )
            throw new Exception("Email address is already taken.");
        if ( usernameIsTaken(name) )
            throw new Exception("Username is already taken.");
        if ( !passwordIsValid(password) )
            throw new Exception("Password is too weak or too short.");

        return register(name, password, email);
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
        String passwordHash = passwordEncoder.encode(password);
        return repository.save(new User(name, passwordHash, email));
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

    private boolean passwordIsValid(String password) {
        return passwordValidator.validate(new PasswordData(password)).isValid();
    }

    private boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}
