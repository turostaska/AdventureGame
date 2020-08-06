package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.NonUsableTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INonUsableToolRepository extends JpaRepository<NonUsableTool, Long> {
    Optional<NonUsableTool> findByName(String name);
}
