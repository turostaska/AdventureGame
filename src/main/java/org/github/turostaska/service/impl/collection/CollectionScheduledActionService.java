package org.github.turostaska.service.impl.collection;

import org.github.turostaska.dao.IScheduledActionDao;
import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.ScheduledAction;
import org.github.turostaska.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IScheduledActionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CollectionScheduledActionService implements IScheduledActionService {
    private IScheduledActionDao dao;

    @Autowired
    private ICharacterService characterService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public CollectionScheduledActionService(IScheduledActionDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(ScheduledAction scheduledAction) {
        if (dao.getById(scheduledAction.getID()).isEmpty())
            dao.create(scheduledAction);
        else
            dao.update(scheduledAction);
    }

    @Override
    public void delete(ScheduledAction scheduledAction) {
        dao.delete(scheduledAction);
    }

    @Override
    public Optional<ScheduledAction> getById(int ID) {
        return dao.getById(ID);
    }

    @Override
    public List<ScheduledAction> getByPlayerName(String playerName) {
        return dao.getByPlayerName(playerName);
    }

    @Override
    public void tryToScheduleActionForPlayer(Player player, Action action) {
        if (player.getActionQueue().contains(action))
            return;

        if (player.ableToTakeOnAction(action)) {
            long timeToFinishWithOtherActionsInSecs = player.getTimeToFinishAllTasksInSeconds();
            long timeToFinishWithThisTaskInSecs =
                    timeToFinishWithOtherActionsInSecs + action.getTimeToFinishInSeconds();

            //todo: id generálás?
            var scheduledAction = new ScheduledAction(
                                    Util.getRandomInteger(0,1000) , action, player,
                                    LocalDateTime.now().plusSeconds(timeToFinishWithThisTaskInSecs)
                                  );

            player.addToActionQueue(scheduledAction);
            //todo: ez így működik?
            scheduler.schedule( () ->  {
                    scheduledAction.trigger();
                    characterService.addOrUpdate(player);
                }, timeToFinishWithThisTaskInSecs, TimeUnit.SECONDS);
            addOrUpdate(scheduledAction);
        }
    }
}
