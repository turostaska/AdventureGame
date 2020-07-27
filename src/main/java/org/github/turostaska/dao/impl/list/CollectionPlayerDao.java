package org.github.turostaska.dao.impl.list;

import org.github.turostaska.dao.IPlayerDao;
import org.github.turostaska.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionPlayerDao implements IPlayerDao {
    private List<Player> players = new ArrayList<>();

    @Override
    public List<Player> getAll() {
        return players;
    }

    @Override
    public Optional<Player> getById(Long ID) {
        for (Player p : players) {
            if (p.getId() == ID)
                return Optional.of(p);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Player> getByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name))
                return Optional.of(p);
        }
        return Optional.empty();
    }

    @Override
    public void update(Player player) {
        for (int i = 0; i < players.size(); ++i) {
            if (players.get(i).equals(player)) {
                players.set(i, player);
                return;
            }
        }
    }

    @Override
    public void delete(Player player) {
        players.remove(player);
    }

    @Override
    public void create(Player player) {
        if (player == null)
            throw new IllegalArgumentException("Player can't be null.");

        if (!players.contains(player))
            players.add(player);
    }

}
