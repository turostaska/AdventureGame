package service;

import domain.User;
import java.util.Optional;

public interface IUserService {
    void addOrUpdate(User user);
    void delete(User user);
    Optional<User> getById(int ID);
    Optional<User> getByName(String name);
    Optional<User> getByEmail(String email);
}
