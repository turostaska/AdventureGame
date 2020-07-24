package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.DuelAction;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.ScheduledTask;
import org.github.turostaska.repository.IScheduledTaskRepository;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RepositoryScheduledTaskService implements IScheduledTaskService {
    @Autowired private IScheduledTaskRepository taskRepository;

    @Autowired private ICharacterService characterService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
        if (player.ableToTakeOnAction(action)) {
            long timeToFinishWithOtherActionsInSecs = player.getTimeToFinishAllTasksInSeconds();
            long timeToFinishWithThisTaskInSecs =
                    timeToFinishWithOtherActionsInSecs + action.getTimeToFinishInSeconds();

            var scheduledTask = new ScheduledTask( action, player, LocalDateTime.now().plusSeconds(timeToFinishWithThisTaskInSecs));

            player.addToActionQueue(scheduledTask);

            scheduler.schedule( () ->  {
                scheduledTask.trigger();
                //player.removeScheduledActionFromQueue(scheduledTask);
                Optional<ScheduledTask> first = player.popScheduledActionFromQueue();
                characterService.addOrUpdate(player);
                first.ifPresent(this::delete);
            }, timeToFinishWithThisTaskInSecs, TimeUnit.SECONDS);
            //addOrUpdate(scheduledTask);
            characterService.addOrUpdate(player);
        }
    }

}
