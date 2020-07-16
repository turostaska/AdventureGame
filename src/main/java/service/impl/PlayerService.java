package service.impl;

import dao.IPlayerDao;
import domain.Player;
import domain.Technique;
import domain.Tool;
import service.IPlayerService;

import java.util.Optional;

public class PlayerService implements IPlayerService {
    private IPlayerDao dao;

    public PlayerService(IPlayerDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(Player player) {
        if (dao.getById(player.getID()).isEmpty())
            dao.create(player);
        else
            dao.update(player);
    }

    @Override
    public void delete(Player player) {
        dao.delete(player);
    }

    @Override
    public Optional<Player> getById(int ID) {
        return dao.getById(ID);
    }

    @Override
    public Optional<Player> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void tryToBuyTool(Player player, Tool item) {
        if (player.tryToBuyTool(item))
            addOrUpdate(player);
    }

    @Override
    public void tryToLearnTechnique(Player player, Technique technique) {
        if (player.tryToLearnTechnique(technique))
            addOrUpdate(player);
    }

}
