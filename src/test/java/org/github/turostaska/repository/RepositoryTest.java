package org.github.turostaska.repository;

import org.github.turostaska.domain.*;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IScheduledTaskService;
import org.github.turostaska.service.IToolService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
//TODO: @DataJpaTest annotációval meg lehet spórolni a rengeteg annotációt, és talán gyorsabb is lesz a teszt indulása, mert nem az egész Spring Context-et
		// indítája, csak ami szükséges.
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
        player.setId(null);

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

    @Autowired private ICharacterService characterService;
    @Autowired private IScheduledTaskService taskService;
    @Autowired private IToolService toolService;

    @Test
    public void duelExecutesAndGetsDeletedAfterward() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        faci.createPlayer();
        User rolcsi = new User("rolcsi", "DamanciaTV7", "rolcsi@cringemail.hu");
        rolcsi.createPlayer();
        userRepository.save(faci);
        userRepository.save(rolcsi);

        DuelAction duel = new DuelAction(3*Action.SECONDS, rolcsi.getPlayer());

        taskService.tryToScheduleDuelActionForPlayer(faci.getPlayer(), duel, rolcsi.getPlayer());

        assertEquals(actionRepository.findAllDuelActions().get(0).getOpponent(), rolcsi.getPlayer());
        assertEquals(((DuelAction)(taskRepository.findAll().get(0).getAction())).getOpponent() , rolcsi.getPlayer());
        assertEquals(actionRepository.findAllDuelActions().size(), 1);
        assertEquals(taskRepository.findAll().size(), 1);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(playerRepository.findByName("rolcsi").get().getMoney() != Player.BASE_MONEY);
        assertEquals(taskRepository.findAll().size(), 0);
        assertEquals(actionRepository.findAllDuelActions().size(), 0);
        assertEquals(actionRepository.findAll().size(), 0);

    }

    @Test
    public void buyingWeapons() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        faci.createPlayer();
        userRepository.save(faci);

        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 200, 20);

        toolService.addOrUpdate(tool);

        characterService.tryToBuyTool(faci.getPlayer(), tool);
        characterService.tryToBuyTool(faci.getPlayer(), tool);
        characterService.tryToBuyTool(faci.getPlayer(), tool);

        Player found = playerRepository.findByName("fáci").get();

        assertEquals(found.getTools().get(tool), 2);


    }
}