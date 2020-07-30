package org.github.turostaska.service.impl.repository;

import com.sun.istack.NotNull;
import lombok.NonNull;
import org.github.turostaska.domain.*;
import org.github.turostaska.domain.Character;
import org.github.turostaska.repository.IScheduledTaskRepository;
import org.github.turostaska.service.IActionService;
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
    @Autowired private IActionService actionService;
    @Autowired private ICharacterService characterService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public ScheduledTask addOrUpdate(ScheduledTask scheduledTask) {
        return taskRepository.save(scheduledTask);
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
    public List<ScheduledTask> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<ScheduledTask> getByPlayer(Player player) {
        return taskRepository.findByPlayer(player);
    }

    @Override
    public void tryToScheduleActionForPlayer(Player player, Action action) {
        if (player.ableToTakeOnAction(action)) {
            long timeToFinishWithOtherActionsInSecs = player.getTimeToFinishAllTasksInSeconds();
            long timeToFinishWithThisTaskInSecs = timeToFinishWithOtherActionsInSecs + action.getTimeToFinishInSeconds();

            player.addToActionQueue(new ScheduledTask( action, player, LocalDateTime.now().plusSeconds(timeToFinishWithThisTaskInSecs)));

            scheduler.schedule( () ->  {
                Optional<Player> playerAtTrigger = characterService.getPlayerById(player.getId());
                if (playerAtTrigger.isPresent()) {
                    playerAtTrigger.get().triggerNextTaskInQueue();
                    characterService.addOrUpdate(playerAtTrigger.get());
                }
            }, timeToFinishWithThisTaskInSecs, TimeUnit.SECONDS);

            Player updatedPlayer = characterService.addOrUpdate(player);
            player.setActionQueue(updatedPlayer.getActionQueue());
        }
    }

    @Override
    public void tryToScheduleDuelActionForPlayer(Player player, DuelAction duelAction, @NonNull Character opponent) {

        if (player.ableToTakeOnAction(duelAction)) {
            Action action = actionService.addOrUpdate(new DuelAction(duelAction.getTimeToFinishInSeconds(), opponent));

            long timeToFinishWithOtherActionsInSecs = player.getTimeToFinishAllTasksInSeconds();
            long timeToFinishWithThisTaskInSecs = timeToFinishWithOtherActionsInSecs + action.getTimeToFinishInSeconds();

            player.addToActionQueue(new StatefulScheduledTask( (DuelAction) action, player, LocalDateTime.now().plusSeconds(timeToFinishWithThisTaskInSecs)));

            scheduler.schedule( () ->  {
                Optional<Player> playerAtTrigger = characterService.getPlayerById(player.getId());
                Optional<Character> opponentAtTrigger = characterService.findById(opponent.getId());
                if (playerAtTrigger.isPresent() && opponentAtTrigger.isPresent()) {
                    ((DuelAction)(playerAtTrigger.get().getActionQueue().get(0).getAction())).setOpponent(opponentAtTrigger.get());
                    playerAtTrigger.get().triggerNextTaskInQueue();
                    characterService.addOrUpdate(playerAtTrigger.get());
                    opponentAtTrigger.get().update(characterService);
                }
            }, timeToFinishWithThisTaskInSecs, TimeUnit.SECONDS);

            Player updatedPlayer = characterService.addOrUpdate(player);
            player.setActionQueue(updatedPlayer.getActionQueue());
        }
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

}
