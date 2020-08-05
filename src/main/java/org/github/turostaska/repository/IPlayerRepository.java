package org.github.turostaska.repository;

import org.github.turostaska.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
