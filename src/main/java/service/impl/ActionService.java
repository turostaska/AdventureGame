package service.impl;

import dao.IActionDao;
import domain.Action;
import domain.RestAction;
import service.IActionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ActionService implements IActionService {
    private IActionDao dao;

    public ActionService(IActionDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(Action action) {
        if (dao.getById(action.getID()).isEmpty())
            dao.create(action);
        else
            dao.update(action);
    }

    @Override
    public void delete(Action action) {
        dao.delete(action);
    }

    @Override
    public Optional<Action> getById(int ID) {
        return dao.getById(ID);
    }

    @Override
    public <T extends Action> List<T> getByType(IActionDao.ActionType type) {
        return dao.getByType(type);
    }

    @Override
    public RestAction getFreeRestAction() {
        Stream<Action> restsStream = getByType(IActionDao.ActionType.REST).stream();
        return (RestAction) restsStream.filter( a -> ((RestAction)a).getCost() == 0 ).findFirst().orElseThrow();
    }
}
