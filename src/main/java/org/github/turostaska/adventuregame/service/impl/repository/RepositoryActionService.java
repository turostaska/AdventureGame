package org.github.turostaska.adventuregame.service.impl.repository;

import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.repository.IActionRepository;
import org.github.turostaska.adventuregame.service.IActionService;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RepositoryActionService implements IActionService {
    @Autowired private IActionRepository repository;

    @Override
    public Action addOrUpdate(Action action) {
        return repository.save(action);
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
    public List<DuelAction> getAllDuelActions() {
        return repository.findAllDuelActions();
    }

    @Override
    public RestAction getFreeRestAction() {
        Stream<RestAction> restsStream = getAllRestActions().stream();
        return restsStream.filter( a -> a.getCost() == 0 ).findFirst().orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Action> getAllStatelessActions() {
        return repository.findAllStatelessActions();
    }

    @Override
    public Optional<Action> getStatelessActionById(Long id) {
        return repository.findStatelessActionById(id);
    }

    @Override
    public Optional<DuelAction> getDuelActionById(Long duelId) {
        return repository.findDuelActionById(duelId);
    }

    @Override
    public boolean isEmpty() {
        return repository.count() == 0;
    }

    @Autowired private ICharacterService characterService;

    @Override
    public boolean carryOutAndGetIfSuccessful(Action action, Player player) {
        return action.carryOutAndGetIfSuccessful(player, characterService);
    }
}
