package org.github.turostaska.repository;

import org.github.turostaska.domain.NonUsableTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INonUsableToolRepository extends JpaRepository<NonUsableTool, Long> {
    Optional<NonUsableTool> findByName(String name);
}
