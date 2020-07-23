package org.github.turostaska.repository;

import org.github.turostaska.domain.*;
import org.github.turostaska.service.IScheduledTaskService;
import org.github.turostaska.service.ITechniqueService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class RepositoryTest {
    @Autowired private TestEntityManager em;

    @Autowired private IUserRepository userRepository;
    @Autowired private IPlayerRepository playerRepository;
    @Autowired private IUsableToolRepository usableToolRepository;
    @Autowired private INonUsableToolRepository nonUsableToolRepository;
    @Autowired private IActionRepository actionRepository;
    @Autowired private ITechniqueRepository techniqueRepository;
    @Autowired private IScheduledTaskRepository taskRepository;

    @Test
    public void addingNewUser() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        User rolcsi = new User("rolcsi", "DamanciaTV7", "rolcsi@cringemail.hu");

        em.persist(faci);
        em.persist(rolcsi);
        em.flush();

        Optional<User> found1 = userRepository.findByUserName("fáci");
        Optional<User> found2 = userRepository.findByUserName("rolcsi");

        assertEquals(found1.get().getUserName(), "fáci");
        assertEquals(found2.get().getUserName(), "rolcsi");
    }

    @Test
    public void registerNewPlayer() {
        User faci = new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        Player player = new Player(faci);
        player.setID(null);

        em.persist(faci);
        em.flush();

        em.persist(player);
        em.flush();

        Optional<Player> found = playerRepository.findByName("fáci");

        assertEquals(found.get().getName(), "fáci");
    }

    @Test
    public void getAllToolsByType() {
        UsableTool usableTool       = new UsableTool("dobócsillag", 5, 0, 400, 20);
        NonUsableTool nonUsableTool = new NonUsableTool("kard", 10, 0, 400, 2);

        em.persist(usableTool);
        em.persist(nonUsableTool);
        em.flush();

        var usableTools = usableToolRepository.findAll();

        assertEquals(usableTools.size(), 1);
    }

    @Test
    public void getActionsByType() {
        RestAction rest = new RestAction(3*RestAction.SECONDS, 0);
        MissionAction mission = new MissionAction(3*RestAction.SECONDS, 1000, 0);

        em.persist(rest);
        em.persist(mission);

        List<RestAction> restActionList = actionRepository.findAllRestActions();
        var missionActionList = actionRepository.findAllMissionActions();
        var adventureActionList = actionRepository.findAllAdventureActions();

        assertEquals(restActionList.size(), 1);
        assertEquals(missionActionList.size(), 1);
        assertEquals(adventureActionList.size(), 0);

    }

    @Test
    public void saveToRepository() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        userRepository.save(faci);
        faci.createPlayer();
        userRepository.save(faci);

        Optional<Player> player = playerRepository.findByName("fáci");

        assertEquals(player.get().getName(), "fáci");
    }

    @Test
    public void savingATaskShouldNotOverwriteAction() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        faci.createPlayer();
        User rolcsi = new User("rolcsi", "DamanciaTV7", "rolcsi@cringemail.hu");
        rolcsi.createPlayer();
        userRepository.save(faci);
        userRepository.save(rolcsi);
        DuelAction duel = new DuelAction(10*Action.MINUTES, null);

        actionRepository.save(duel);

        ScheduledTask scheduledTask = new ScheduledTask(duel, faci.getPlayer(), LocalDateTime.now().plusSeconds(duel.getTimeToFinishInSeconds()));
        taskRepository.save(scheduledTask);

        assertEquals(actionRepository.findAllDuelActions().get(0).getOpponent(), null);
        assertEquals(((DuelAction)(taskRepository.findAll().get(0).getAction())).getOpponent() , rolcsi.getPlayer());
        assertEquals(actionRepository.findAllDuelActions().size(), 1);

    }
}