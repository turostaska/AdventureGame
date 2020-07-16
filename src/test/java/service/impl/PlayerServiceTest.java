package service.impl;

import dao.IPlayerDao;
import domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerServiceTest {
    private IPlayerDao dao;
    private User user;
    private Player player;

    @InjectMocks
    private PlayerService service;

    @BeforeEach
    public void setUp() {
        dao = mock(IPlayerDao.class);
        service = new PlayerService(dao);
        user = new User("Laci", "Kutya123", "asd@fgh.hu");
        player = new Player(user);
        service.addOrUpdate(player);
    }

    @Test
    public void buyToolWithEnoughMoney() {
        NonUsableTool tool = new NonUsableTool("kard", 10, 0, 400, 2);

        service.tryToBuyTool(player, tool);

        assertEquals(100, player.getMoney());
        assertEquals(player.getTools().get(tool), (Integer) 1);
    }

    @Test
    public void tryToLearnTechniqueWithoutEnoughMoney() {
        Technique technique = new Technique("tűzgolyó", 100, 0, 2000, 40);

        service.tryToLearnTechnique(player, technique);

        assertEquals(500, player.getMoney());
        assertEquals(player.getKnownTechniques().size(), 0);
    }

    @Test
    public void useNonUsableTool() {
        NonUsableTool tool = new NonUsableTool("kard", 10, 0, 400, 2);
        service.tryToBuyTool(player, tool);
        User otherUser = new User("Feri", "Cica123", "qwe@fgh.hu");
        Player opponent = new Player(otherUser);

        tool.useInDuel(player, opponent);

        assertEquals(90, opponent.getCurrentHP());
        assertEquals(100, player.getCurrentHP());
        assertEquals(player.getTools().get(tool), (Integer) 1);
    }

    @Test
    public void useUsableTool() {
        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 400, 20);
        service.tryToBuyTool(player, tool);
        User otherUser = new User("Feri", "Cica123", "qwe@fgh.hu");
        Player opponent = new Player(otherUser);

        tool.useInDuel(player, opponent);

        assertEquals(95, opponent.getCurrentHP());
        assertEquals(100, player.getCurrentHP());
        assertEquals(player.getTools().size(), 0);
    }

    //todo: technique usage test

}