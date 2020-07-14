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
        if (dao.getById(user.getID()).equals(Optional.empty()))
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
}
