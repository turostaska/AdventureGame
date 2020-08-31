package org.github.turostaska.adventuregame.service.impl.collection;

import jdk.jshell.spi.ExecutionControl;
import org.github.turostaska.adventuregame.dao.INPCDao;
import org.github.turostaska.adventuregame.dao.IPlayerDao;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.IActionService;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionCharacterService implements ICharacterService {

    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    private INPCDao npcDao;

    @Autowired
    private IActionService actionService;

    public CollectionCharacterService(IPlayerDao playerDao, INPCDao npcDao) {
        this.playerDao = playerDao;
        this.npcDao = npcDao;
    }

    @Override
    public Player addOrUpdate(Player player) {
        if (playerDao.getById(player.getId()).isEmpty())
            playerDao.create(player);
        else
            playerDao.update(player);
        return getPlayerByName(player.getName()).orElseThrow(IllegalStateException::new);
    }

    @Override
    public void delete(Player player) {
        playerDao.delete(player);
    }

    @Override
    public Optional<Player> getPlayerById(Long ID) {
        return playerDao.getById(ID);
    }

    @Override
    public Optional<Player> getPlayerByName(String name) {
        return playerDao.getByName(name);
    }

    @Override
    public Optional<Player> tryToBuyTool(Player player, Tool item) {
        if (player.tryToBuyTool(item))
            return Optional.of(addOrUpdate(player));

        return Optional.empty();
    }

    @Override
    public void obtainTool(NPC npc, Tool tool) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Player> tryToLearnTechnique(Player player, Technique technique) {
        if (player.tryToLearnTechnique(technique))
            return Optional.of(addOrUpdate(player));

        return Optional.empty();
    }

    @Override
    public void learnTechnique(NPC npc, Technique technique) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOrUpdate(NPC npc) {
        if (npcDao.getById(npc.getId()).isEmpty())
            npcDao.create(npc);
        else
            npcDao.update(npc);
    }

    @Override
    public void delete(NPC npc) {
        npcDao.delete(npc);
    }

    @Override
    public Optional<NPC> getNPCById(Long ID) {
        return npcDao.getById(ID);
    }

    @Override
    public Optional<NPC> getNPCByName(String name) {
        return npcDao.getByName(name);
    }

    @Override
    public void useInDuel(Tool tool, Character usedBy, Character usedAgainst) {
        if (!usedBy.possessesTool(tool))
            return;

        usedBy.heal(tool.getHealingAmount());
        usedAgainst.takeDamage(tool.getDamage());

        tool.useUp(usedBy);

        usedBy.update(this);
        usedAgainst.update(this);
    }

    @Override
    public void useInDuel(Technique technique, Character usedBy, Character usedAgainst) {
        if (usedBy.getCurrentMana() < technique.getCostToCast() || !usedBy.knowsTechnique(technique))
            return;

        usedBy.heal(technique.getHealingAmount());
        usedAgainst.takeDamage(technique.getDamage());
        usedBy.loseMana(technique.getCostToCast());

        usedBy.update(this);
        usedAgainst.update(this);
    }

    @Override
    public void takeDamage(NPC who, int amount) {
        who.takeDamage(amount);
    }

    @Override
    public void takeDamage(Player who, int amount) {
        who.takeDamage(amount);

        if (!who.isAlive())
            forceToHaveARest(who);

        who.update(this);
    }

    private void forceToHaveARest(Player who) {
        who.clearActionQueue();
        who.ableToTakeOnAction(actionService.getFreeRestAction());
    }

    @Override
    public void heal(Character who, int amount) {
        who.heal(amount);
    }

    @Override
    public List<Player> getAllPlayers() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<NPC> getAllNPCs() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deletePlayerById(Long id) {
        playerDao.getById(id).ifPresent(playerDao::delete);
    }

    @Override
    public Optional<Character> findById(Long id) {
        if (getPlayerById(id).isPresent())
            return Optional.of(getPlayerById(id).get());
        if (getNPCById(id).isPresent())
            return Optional.of(getNPCById(id).get());
        return Optional.empty();
    }

    @Override
    public List<Character> getAll() {
        return Stream.concat(getAllPlayers().stream(), getAllNPCs().stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean npcsArePresent() {
        try {
            throw new ExecutionControl.NotImplementedException("implementálja akinek két anyja van");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void triggerNextTaskInQueue(Player player) {
        try {
            throw new ExecutionControl.NotImplementedException("implementálja akinek két anyja van");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void kill(Character character) {
        if (character instanceof Player) {
            Player player = ((Player) character);
            takeDamage(player, player.getMaxHP());
        }
    }

    private void kill(Player who) {
        takeDamage(who, who.getMaxHP());
    }

}
