package service.impl;

import dao.IPlayerDao;
import dao.impl.CollectionPlayerDao;
import domain.Player;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerServiceTest {
    private IPlayerDao dao;
    private PlayerService service;
    private Player player;

    @BeforeEach
    public void setUp() {
        dao = new CollectionPlayerDao();
        service = new PlayerService(dao);
        player = new Player("Laci");
        service.addOrUpdate(player);
    }

    @Test
    public void addPlayer() {
        assertEquals(player, service.getByName("Laci").get());
    }

    @Test
    public void addNullPlayerFails() {
        assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }

    @Test
    public void updatePlayer() {
        player.addMoney(1000);
        service.addOrUpdate(player);
        assertEquals(1500, dao.getById(1).get().getMoney());
    }

    @Test
    public void deletePlayer() {
        player.addMoney(500);
        service.delete(player);
    }



}