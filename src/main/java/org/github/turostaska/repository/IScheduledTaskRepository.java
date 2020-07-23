package org.github.turostaska.repository;

import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {

    List<ScheduledTask> findByPlayer(Player player);

}
