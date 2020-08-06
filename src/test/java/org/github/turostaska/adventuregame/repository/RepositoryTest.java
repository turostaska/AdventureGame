package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.IScheduledTaskService;
import org.github.turostaska.adventuregame.service.IToolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//idk hogy ez így most működne-e de a régi annotációkkal se megy mert h2-t akar használni szóval annyira mindegy lmao
class RepositoryTest {
    @Autowired private IUserRepository userRepository;
    @Autowired private IPlayerRepository playerRepository;
    @Autowired private IUsableToolRepository usableToolRepository;
    @Autowired private INonUsableToolRepository nonUsableToolRepository;
    @Autowired private IActionRepository actionRepository;
    @Autowired private ITechniqueRepository techniqueRepository;
    @Autowired private IScheduledTaskRepository taskRepository;

    @Test
    public void addingNewUser() {
        User faci =   new User("fácilmente", "AmyGlassires99", "facil@mente.hu");
        User rolcsi = new User("rolacs", "DamanciaTV7", "rolcsi@cringomail.hu");

        userRepository.save(faci);
        userRepository.save(rolcsi);

        Optional<User> found1 = userRepository.findByUserName("fácilmente");
        Optional<User> found2 = userRepository.findByUserName("rolacs");

        assertEquals(found1.get().getUserName(), "fácilmente");
        assertEquals(found2.get().getUserName(), "rolacs");
    }

    @Test
    public void registerNewPlayer() {
        User faci = new User("fácilmente", "AmyGlassires99", "facil@mente.hu");
        var player = faci.createPlayer();

        userRepository.save(faci);
        playerRepository.save(player);

        Optional<Player> found = playerRepository.findByName("fácilmente");

        assertEquals(found.get().getName(), "fácilmente");
    }

    @Test
    public void getAllToolsByType() {
        UsableTool usableTool       = new UsableTool("dobókés", 5, 0, 400, 20);
        NonUsableTool nonUsableTool = new NonUsableTool("kardok", 10, 0, 400, 2);

        usableToolRepository.save(usableTool);
        nonUsableToolRepository.save(nonUsableTool);

        var usableTools = usableToolRepository.findAll();

        assertNotEquals(usableTools.size(), 0);
    }

    @Test
    public void getActionsByType() {
        RestAction rest = new RestAction(3*RestAction.SECONDS, 0);
        MissionAction mission = new MissionAction(3*RestAction.SECONDS, 1000, 0);

        actionRepository.save(rest);
        actionRepository.save(mission);

        List<RestAction> restActionList = actionRepository.findAllRestActions();
        var missionActionList = actionRepository.findAllMissionActions();
        var adventureActionList = actionRepository.findAllAdventureActions();

        assertNotEquals(restActionList.size(), 0);
        assertNotEquals(missionActionList.size(), 0);
    }

    @Test
    public void saveToRepository() {
        User faci = new User("fácilmente", "AmyGlassires99", "facil@mente.hu");
        userRepository.save(faci);
        faci.createPlayer();
        userRepository.save(faci);

        Optional<Player> player = playerRepository.findByName("fácilmente");

        assertEquals(player.get().getName(), "fácilmente");
    }

    @Autowired private ICharacterService characterService;
    @Autowired private IScheduledTaskService taskService;
    @Autowired private IToolService toolService;

    @Test
    public void duelExecutesAndGetsDeletedAfterward() throws InterruptedException {
        User faci =   new User("fácilmente", "AmyGlassires99", "facil@mente.hu");
        faci.createPlayer();
        User rolcsi = new User("rolacs", "DamanciaTV7", "rolcsi@cringomail.hu");
        rolcsi.createPlayer();
        userRepository.save(faci);
        userRepository.save(rolcsi);

        DuelAction duel = new DuelAction(3*Action.SECONDS, rolcsi.getPlayer());

        taskService.tryToScheduleDuelActionForPlayer(faci.getPlayer(), duel, rolcsi.getPlayer());

        assertEquals(actionRepository.findAllDuelActions().get(0).getOpponent(), rolcsi.getPlayer());
        assertEquals(((DuelAction)(taskRepository.findAll().get(0).getAction())).getOpponent() , rolcsi.getPlayer());
        assertEquals(actionRepository.findAllDuelActions().size(), 1);
        assertEquals(taskRepository.findAll().size(), 1);

        TimeUnit.SECONDS.sleep(5);

        assertTrue(playerRepository.findByName("rolacs").get().getMoney() != Player.BASE_MONEY);
        assertEquals(taskRepository.findAll().size(), 0);
        assertEquals(actionRepository.findAllDuelActions().size(), 0);
        assertEquals(actionRepository.findAll().size(), 0);

    }

    @Test
    public void buyingWeapons() {
        User faci =   new User("fácilmente", "AmyGlassires99", "facil@mente.hu");
        faci.createPlayer();
        userRepository.save(faci);

        UsableTool tool = new UsableTool("dobókés", 5, 0, 200, 20);

        toolService.addOrUpdate(tool);

        characterService.tryToBuyTool(faci.getPlayer(), tool);
        characterService.tryToBuyTool(faci.getPlayer(), tool);
        characterService.tryToBuyTool(faci.getPlayer(), tool);

        Player found = playerRepository.findByName("fácilmente").get();

        assertEquals(found.getTools().get(tool), 2);


    }
}