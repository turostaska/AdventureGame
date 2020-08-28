package org.github.turostaska.adventuregame.service.impl.collection;

import jdk.jshell.spi.ExecutionControl;
import org.github.turostaska.adventuregame.dao.IActionDao;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.IActionService;

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
    public List<DuelAction> getAllDuelActions() {
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

    @Override
    public List<Action> getAllStatelessActions() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Action> getStatelessActionById(Long id) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<DuelAction> getDuelActionById(Long duelId) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean isEmpty() {
        try {
            throw new ExecutionControl.NotImplementedException("implementálja akinek két anyja van");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Action action, Player player) {
        try {
            throw new ExecutionControl.NotImplementedException("implementálja akinek két anyja van");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
