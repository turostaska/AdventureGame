package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.ScheduledTask;
import org.github.turostaska.repository.IScheduledTaskRepository;
import org.github.turostaska.service.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryScheduledTaskService implements IScheduledTaskService {
    @Autowired private IScheduledTaskRepository taskRepository;

    @Override
    public void addOrUpdate(ScheduledTask scheduledTask) {
        taskRepository.save(scheduledTask);
    }

    @Override
    public void delete(ScheduledTask scheduledTask) {
        taskRepository.delete(scheduledTask);
    }

    @Override
    public Optional<ScheduledTask> getById(Long ID) {
        return taskRepository.findById(ID);
    }

    @Override
    public List<ScheduledTask> getByPlayer(Player player) {
        return taskRepository.findByPlayer(player);
    }

    @Override
    public void tryToScheduleActionForPlayer(Player player, Action action) {

    }
}
