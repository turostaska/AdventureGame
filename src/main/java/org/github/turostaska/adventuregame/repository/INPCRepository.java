package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.NPC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INPCRepository extends JpaRepository<NPC, Long> {
    Optional<NPC> findByName(String name);
}
