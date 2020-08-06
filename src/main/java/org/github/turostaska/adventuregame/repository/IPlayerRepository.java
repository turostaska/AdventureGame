package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
