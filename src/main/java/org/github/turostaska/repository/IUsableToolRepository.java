package org.github.turostaska.repository;

import org.github.turostaska.domain.UsableTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsableToolRepository extends JpaRepository<UsableTool, Long> {
    Optional<UsableTool> findByName(String name);

}
