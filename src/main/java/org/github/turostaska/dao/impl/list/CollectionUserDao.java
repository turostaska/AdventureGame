package org.github.turostaska.dao.impl.list;

import org.github.turostaska.dao.IUserDao;
import org.github.turostaska.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionUserDao implements IUserDao {
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public Optional<User> getById(Long ID) {
        for (User u : users) {
            if (u.getId() == ID)
                return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByName(String name) {
        for (User u : users) {
            if (u.getUserName().equals(name))
                return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email))
                return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return;
            }
        }
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public void create(User user) {
        if (user == null)
            throw new IllegalArgumentException("Player can't be null.");

        if (!users.contains(user))
            users.add(user);
    }
}
