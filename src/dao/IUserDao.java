package dao;

import domain.User;
import java.util.List;
import java.util.Optional;

public interface IUserDao {
    List<User> getAll();
    Optional<User> getById(int ID);
    Optional<User> getByName(String name);
    Optional<User> getByEmail(String email);
    void update(User user);
    void delete(User user);
    void create(User user);
}
