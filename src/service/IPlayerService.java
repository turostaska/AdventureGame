package service;

import domain.Player;
import java.util.Optional;

public interface IPlayerService {
    void addOrUpdate(Player player);
    void delete(Player player);
    Optional<Player> getById(int ID);
    Optional<Player> getByName(String name);
}
