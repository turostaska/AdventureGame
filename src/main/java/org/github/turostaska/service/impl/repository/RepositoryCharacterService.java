package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.*;
import org.github.turostaska.domain.Character;
import org.github.turostaska.repository.INPCRepository;
import org.github.turostaska.repository.IPlayerRepository;
import org.github.turostaska.service.IActionService;
import org.github.turostaska.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryCharacterService implements ICharacterService {

    @Autowired private IPlayerRepository playerRepository;
    @Autowired private INPCRepository npcRepository;

    @Autowired private IActionService actionService;

    @Override
    public Player addOrUpdate(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public Optional<Player> getPlayerById(Long ID) {
        return playerRepository.findById(ID);
    }

    @Override
    public Optional<Player> getPlayerByName(String name) {
        return playerRepository.findByName(name);
    }

    @Override
    public void tryToBuyTool(Player player, Tool item) {
        if (player.tryToBuyTool(item)) {
            addOrUpdate(player);
        }
    }

    @Override
    public void obtainTool(NPC npc, Tool tool) {
        npc.obtainTool(tool);
        addOrUpdate(npc);
    }

    @Override
    public void tryToLearnTechnique(Player player, Technique technique) {
        if (player.tryToLearnTechnique(technique))
            addOrUpdate(player);
    }

    @Override
    public void learnTechnique(NPC npc, Technique technique) {
        npc.learnTechnique(technique);
        addOrUpdate(npc);
    }

    @Override
    public void addOrUpdate(NPC npc) {
        npcRepository.save(npc);
    }

    @Override
    public void delete(NPC npc) {
        npcRepository.delete(npc);
    }

    @Override
    public Optional<NPC> getNPCById(Long ID) {
        return npcRepository.findById(ID);
    }

    @Override
    public Optional<NPC> getNPCByName(String name) {
        return npcRepository.findByName(name);
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
        return playerRepository.findAll();
    }

    @Override
    public List<NPC> getAllNPCs() {
        return npcRepository.findAll();
    }

    @Override
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Optional<Character> findById(Long id) {
        if (getPlayerById(id).isPresent())
            return Optional.of(getPlayerById(id).get());
        if (getNPCById(id).isPresent())
            return Optional.of(getNPCById(id).get());
        return Optional.empty();
    }

}
