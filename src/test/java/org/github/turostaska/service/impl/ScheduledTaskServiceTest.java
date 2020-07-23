package org.github.turostaska.service.impl;

import org.github.turostaska.dao.IScheduledActionDao;
import org.github.turostaska.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.github.turostaska.service.IScheduledTaskService;
import org.github.turostaska.service.impl.collection.CollectionScheduledTaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ScheduledTaskServiceTest {
    private Player player;

    @InjectMocks
    private IScheduledTaskService service;

    @BeforeEach
    public void setUp() {
        IScheduledActionDao dao = mock(IScheduledActionDao.class);
        service = new CollectionScheduledTaskService(dao);
        User user = new User("Laci", "Kutya123", "asd@fgh.hu");
        player = new Player(user);
    }

    @Test
    public void cannotHaveMoreActionsThanAllowed() {
        RestAction action = new RestAction(8*RestAction.HOURS, 50);

        service.tryToScheduleActionForPlayer(player, action);
        service.tryToScheduleActionForPlayer(player, action);
        service.tryToScheduleActionForPlayer(player, action);
        service.tryToScheduleActionForPlayer(player, action);
        service.tryToScheduleActionForPlayer(player, action);
        service.tryToScheduleActionForPlayer(player, action);

        assertEquals(player.getMoney(), Player.MAX_NUMBER_OF_ACTIONS * action.getCost());
        assertEquals(player.getActionQueue().size(), Player.MAX_NUMBER_OF_ACTIONS);
    }

    @Test
    public void haveASuccessfulMission() {
        MissionAction mission = new MissionAction(3*RestAction.SECONDS, 1000, 0);

        service.tryToScheduleActionForPlayer(player, mission);
        assertEquals(player.getActionQueue().size(), 1);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(player.getMoney(), Player.BASE_MONEY + 1000);
        assertEquals(player.getActionQueue().size(), 0);
    }

    @Test
    public void cannotAffordExpensiveRest() {
        RestAction action = new RestAction(8*RestAction.HOURS, 600);

        service.tryToScheduleActionForPlayer(player, action);

        assertEquals(player.getMoney(), Player.BASE_MONEY);
        assertEquals(player.getActionQueue().size(), 0);
    }

    @Test
    public void takingARestHasItsEffect() {
        RestAction action = new RestAction(3*RestAction.SECONDS, 0);

        player.takeDamage(50);
        player.restoreMana(-50);

        assertEquals(player.getCurrentHP(), player.getMaxHP() - 50);
        assertEquals(player.getCurrentMana(), player.getMaxMana() - 50);

        service.tryToScheduleActionForPlayer(player, action);

        assertEquals(player.getActionQueue().size(), 1);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(player.getCurrentHP(), player.getMaxHP());
        assertEquals(player.getCurrentMana(), player.getMaxMana());
        assertEquals(player.getActionQueue().size(), 0);
    }

    @Test
    public void successfulAdventure() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new NonUsableTool("kard", 10, 0, 400, 2));
        tools.add(new UsableTool("dobócsillag", 5, 0, 400, 20));
        List<Technique> techniques = new ArrayList<>();
        techniques.add( new Technique("nagy tűzgolyó", 200, 0, 500, 90) );

        AdventureAction adventureAction = new AdventureAction(3*AdventureAction.SECONDS, techniques, tools, 0);

        service.tryToScheduleActionForPlayer(player, adventureAction);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(player.getTools().size(), tools.size());
        assertEquals(player.getKnownTechniques().size(), techniques.size());
    }

}