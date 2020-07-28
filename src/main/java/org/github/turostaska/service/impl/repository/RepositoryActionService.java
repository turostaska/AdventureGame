package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.AdventureAction;
import org.github.turostaska.domain.MissionAction;
import org.github.turostaska.domain.RestAction;
import org.github.turostaska.repository.IActionRepository;
import org.github.turostaska.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RepositoryActionService implements IActionService {
    @Autowired private IActionRepository repository;

    @Override
    public void addOrUpdate(Action action) {
        repository.save(action);
    }

    @Override
    public void delete(Action action) {
        repository.delete(action);
    }

    @Override
    public Optional<Action> getById(Long ID) {
        return repository.findById(ID);
    }

    @Override
    public List<RestAction> getAllRestActions() {
        return repository.findAllRestActions();
    }

    @Override
    public List<AdventureAction> getAllAdventureActions() {
        return repository.findAllAdventureActions();
    }

    @Override
    public List<MissionAction> getAllMissionActions() {
        return repository.findAllMissionActions();
    }

    @Override
    public RestAction getFreeRestAction() {
        //todo: erre szerintem nem lesz külön szükség
        Stream<RestAction> restsStream = getAllRestActions().stream();
        return restsStream.filter( a -> a.getCost() == 0 ).findFirst().orElseThrow();
    }
}