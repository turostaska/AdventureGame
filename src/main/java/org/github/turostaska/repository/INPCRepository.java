package org.github.turostaska.repository;

import org.github.turostaska.domain.NPC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INPCRepository extends JpaRepository<NPC, Long> {
    Optional<NPC> findByName(String name);
}
