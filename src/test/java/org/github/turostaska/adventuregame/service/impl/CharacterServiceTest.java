package org.github.turostaska.adventuregame.service.impl;

import org.github.turostaska.adventuregame.dao.INPCDao;
import org.github.turostaska.adventuregame.dao.IPlayerDao;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.github.turostaska.adventuregame.service.impl.collection.CollectionCharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: a Mockito használatban itt érzek némi bizonytalanságot, nézd át szerintem a használatát mégegyszer, vagy ha kérdés van, akkor beszéljük át.
public class CharacterServiceTest {
    private Player player;
    private Player opponent;

    @InjectMocks
    private ICharacterService service;

    @BeforeEach
    public void setUp() {
        IPlayerDao playerDao = mock(IPlayerDao.class);
        INPCDao npcDao = mock(INPCDao.class);
        service = new CollectionCharacterService(playerDao, npcDao);

        User user = new User("Laci", "Kutya123", "asd@fgh.hu");
        player = new Player(user);
        User otherUser = new User("Feri", "Cica123", "qwe@fgh.hu");
        opponent = new Player(otherUser);

        when(playerDao.getByName("Laci")).thenReturn(java.util.Optional.of(player));
        when(playerDao.getByName("Feri")).thenReturn(java.util.Optional.of(opponent));

        service.addOrUpdate(player);
        service.addOrUpdate(opponent);
    }

    @Test
    public void buyToolWithEnoughMoney() {
        NonUsableTool tool = new NonUsableTool("kard", 10, 0, 400, 2);

        service.tryToBuyTool(player, tool);

        assertEquals(Player.BASE_MONEY - 400, player.getMoney());
        assertEquals(player.getTools().get(tool), (Integer) 1);
    }

    @Test
    public void tryToLearnTechniqueWithoutEnoughMoney() {
        Technique technique = new Technique("tűzgolyó", 100, 0, 2000, 40);

        service.tryToLearnTechnique(player, technique);

        assertEquals(Player.BASE_MONEY, player.getMoney());
        assertEquals(player.getKnownTechniques().size(), 0);
    }

    @Test
    public void useNonUsableTool() {
        NonUsableTool tool = new NonUsableTool("kard", 10, 0, 400, 2);
        service.tryToBuyTool(player, tool);

        service.useInDuel(tool, player, opponent);

        assertEquals(Player.BASE_HP - 10, opponent.getCurrentHP());
        assertEquals(Player.BASE_HP, player.getCurrentHP());
        assertEquals(player.getTools().get(tool), (Integer) 1);
    }

    @Test
    public void useUsableTool() {
        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 400, 20);
        service.tryToBuyTool(player, tool);

        service.useInDuel(tool, player, opponent);

        assertEquals(Player.BASE_HP - 5, opponent.getCurrentHP());
        assertEquals(Player.BASE_HP, player.getCurrentHP());
        assertEquals(player.getTools().size(), 0);
    }

    @Test
    public void useTechniqueWithEnoughMana() {
        Technique technique = new Technique("nagy tűzgolyó", 200, 0, 500, 90);
        service.tryToLearnTechnique(player, technique);

        service.useInDuel(technique, player, opponent);

        assertEquals(Math.max(0, Player.BASE_HP - 200), opponent.getCurrentHP());
        assertEquals(Player.BASE_HP, player.getCurrentHP());
        assertEquals(Player.BASE_MANA - 90, player.getCurrentMana());
    }

    @Test
    public void useTechniqueWithoutLearningItFirst() {
        Technique technique = new Technique("nagy tűzgolyó", 200, 0, 500, 90);

        service.useInDuel(technique, player, opponent);

        assertEquals(Player.BASE_HP, opponent.getCurrentHP());
        assertEquals(Player.BASE_MONEY, player.getMoney());
        assertEquals(Player.BASE_MANA, player.getCurrentMana());
        assertEquals(player.getKnownTechniques().size(), 0);
    }

    @Test
    public void useToolWithoutBuyingIt() {
        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 400, 20);

        service.useInDuel(tool, player, opponent);

        assertEquals(Player.BASE_HP, opponent.getCurrentHP());
        assertEquals(Player.BASE_MONEY, player.getMoney());
        assertEquals(player.getTools().size(), 0);
    }

    @Test
    public void buyingAToolMakesItsUserStronger() {
        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 400, 20);

        assertEquals(player.getStrength(), 0);

        service.tryToBuyTool(player, tool);

        assertEquals(player.getStrength(), tool.getStrengthIncrease());
    }

    @Test
    public void usingUpAToolMakesItsUserWeaker() {
        UsableTool tool = new UsableTool("dobócsillag", 5, 0, 400, 20);

        service.tryToBuyTool(player, tool);

        service.useInDuel(tool, player, opponent);

        assertEquals(player.getStrength(), 0);
    }



}