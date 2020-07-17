package service.impl;

import dao.INPCDao;
import dao.IPlayerDao;
import domain.NPC;
import domain.Player;
import domain.Technique;
import domain.Tool;
import domain.Character;
import service.ICharacterService;

import java.util.Optional;

public class CharacterService implements ICharacterService {
    private IPlayerDao playerDao;
    private INPCDao npcDao;

    public CharacterService(IPlayerDao playerDao, INPCDao npcDao) {
        this.playerDao = playerDao;
        this.npcDao = npcDao;
    }

    @Override
    public void addOrUpdate(Player player) {
        if (playerDao.getById(player.getID()).isEmpty())
            playerDao.create(player);
        else
            playerDao.update(player);
    }

    @Override
    public void delete(Player player) {
        playerDao.delete(player);
    }

    @Override
    public Optional<Player> getPlayerById(int ID) {
        return playerDao.getById(ID);
    }

    @Override
    public Optional<Player> getPlayerByName(String name) {
        return playerDao.getByName(name);
    }

    @Override
    public void tryToBuyTool(Player player, Tool item) {
        if (player.tryToBuyTool(item))
            addOrUpdate(player);
    }

    @Override
    public void tryToLearnTechnique(Player player, Technique technique) {
        if (player.tryToLearnTechnique(technique))
            addOrUpdate(player);
    }

    @Override
    public void addOrUpdate(NPC npc) {
        if (npcDao.getById(npc.getID()).isEmpty())
            npcDao.create(npc);
        else
            npcDao.update(npc);
    }

    @Override
    public void delete(NPC npc) {
        npcDao.delete(npc);
    }

    @Override
    public Optional<NPC> getNPCById(int ID) {
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
    }

    private void forceToHaveARest(Player who) {
        who.clearActionQueue();
        //TODO: hozzáadni a pihenő actiont
    }

    @Override
    public void heal(Character who, int amount) {
        who.heal(amount);
    }

}
