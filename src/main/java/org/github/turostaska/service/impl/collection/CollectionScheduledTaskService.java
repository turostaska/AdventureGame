package org.github.turostaska.service.impl.collection;

import org.github.turostaska.dao.IScheduledActionDao;
import org.github.turostaska.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IScheduledTaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CollectionScheduledTaskService implements IScheduledTaskService {
    private IScheduledActionDao dao;

    @Autowired
    private ICharacterService characterService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public CollectionScheduledTaskService(IScheduledActionDao dao) {
        this.dao = dao;
    }

    @Override
    public ScheduledTask addOrUpdate(ScheduledTask scheduledTask) {
        if (dao.getById(scheduledTask.getId()).isEmpty())
            dao.create(scheduledTask);
        else
            dao.update(scheduledTask);

        return dao.getById(scheduledTask.getId()).get();
    }

    @Override
    public void delete(ScheduledTask scheduledTask) {
        dao.delete(scheduledTask);
    }

    @Override
    public Optional<ScheduledTask> getById(Long ID) {
        return dao.getById(ID);
    }

    @Override
    public List<ScheduledTask> getAll() {
        return dao.getAll();
    }

    @Override
    public List<ScheduledTask> getByPlayer(Player player) {
        return dao.getByPlayerName(player.getName());
    }

    @Override
    public void tryToScheduleActionForPlayer(Player player, Action action) {
        if (player.ableToTakeOnAction(action)) {
            long timeToFinishWithOtherActionsInSecs = player.getTimeToFinishAllTasksInSeconds();
            long timeToFinishWithThisTaskInSecs =
                    timeToFinishWithOtherActionsInSecs + action.getTimeToFinishInSeconds();

            var scheduledTask = new ScheduledTask(
                                    action, player,
                                    LocalDateTime.now().plusSeconds(timeToFinishWithThisTaskInSecs)
                                  );

            player.addToActionQueue(scheduledTask);

            scheduler.schedule( () ->  {
                    scheduledTask.trigger();
                    characterService.addOrUpdate(player);
                }, timeToFinishWithThisTaskInSecs, TimeUnit.SECONDS);
            addOrUpdate(scheduledTask);
        }
    }

    @Override
    public void deleteById(Long id) {
        getById(id).ifPresent(this::delete);
    }
}
