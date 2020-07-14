package dao;

import domain.Player;

import java.util.List;
import java.util.Optional;

public interface IPlayerDao {
    List<Player> getAll();
    Optional<Player> getById(int ID);
    Optional<Player> getByName(String name);
    void update(Player player);
    void delete(Player player);
    void create(Player player);
}
