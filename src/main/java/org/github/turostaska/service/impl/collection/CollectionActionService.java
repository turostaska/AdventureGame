package org.github.turostaska.service.impl.collection;

import jdk.jshell.spi.ExecutionControl;
import org.github.turostaska.dao.IActionDao;
import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.AdventureAction;
import org.github.turostaska.domain.MissionAction;
import org.github.turostaska.domain.RestAction;
import org.github.turostaska.service.IActionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CollectionActionService implements IActionService {
    private IActionDao dao;

    public CollectionActionService(IActionDao dao) {
        this.dao = dao;
    }

    @Override
    public Action addOrUpdate(Action action) {
        if (dao.getById(action.getId()).isEmpty())
            dao.create(action);
        else
            dao.update(action);

        return dao.getById(action.getId()).get();
    }

    @Override
    public void delete(Action action) {
        dao.delete(action);
    }

    @Override
    public Optional<Action> getById(Long ID) {
        return dao.getById(ID);
    }

    @Override
    public List<RestAction> getAllRestActions() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AdventureAction> getAllAdventureActions() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MissionAction> getAllMissionActions() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RestAction getFreeRestAction() {
        Stream<RestAction> restsStream = getAllRestActions().stream();
        return restsStream.filter( a -> a.getCost() == 0 ).findFirst().orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        dao.getById(id).ifPresent(dao::delete);
    }
}
