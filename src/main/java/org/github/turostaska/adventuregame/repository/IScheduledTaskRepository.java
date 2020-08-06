package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.Player;
import org.github.turostaska.adventuregame.domain.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {

    List<ScheduledTask> findByPlayer(Player player);

}
