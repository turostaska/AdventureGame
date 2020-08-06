package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.Technique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITechniqueRepository extends JpaRepository<Technique, Long> {
    Optional<Technique> findByName(String name);
}
