package org.github.turostaska.repository;

import org.github.turostaska.domain.Technique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITechniqueRepository extends JpaRepository<Technique, Long> {
    Optional<Technique> findByName(String name);
}
