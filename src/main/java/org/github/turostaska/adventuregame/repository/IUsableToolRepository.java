package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.UsableTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsableToolRepository extends JpaRepository<UsableTool, Long> {
    Optional<UsableTool> findByName(String name);

}
