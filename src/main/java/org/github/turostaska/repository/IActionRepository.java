package org.github.turostaska.repository;

import org.github.turostaska.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IActionRepository extends JpaRepository<Action, Long> {

    @Query("select a from Action a where TYPE(a) = RestAction")
    List<RestAction> findAllRestActions();

    @Query("select a from Action a where TYPE(a) = MissionAction")
    List<MissionAction> findAllMissionActions();

    @Query("select a from Action a where TYPE(a) = DuelAction")
    List<DuelAction> findAllDuelActions();

    @Query("select a from Action a where TYPE(a) = AdventureAction")
    List<AdventureAction> findAllAdventureActions();
}
