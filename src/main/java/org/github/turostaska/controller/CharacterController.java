package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.NpcModelAssembler;
import org.github.turostaska.controller.assembler.PlayerModelAssembler;
import org.github.turostaska.domain.*;
import org.github.turostaska.domain.Character;
import org.github.turostaska.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private ICharacterService characterService;
    @Autowired private IToolService toolService;
    @Autowired private ITechniqueService techniqueService;
    @Autowired private IActionService actionService;
    @Autowired private IScheduledTaskService taskService;
    @Autowired private PlayerModelAssembler playerModelAssembler;
    @Autowired private NpcModelAssembler npcModelAssembler;

    @GetMapping("/players")
    public CollectionModel<EntityModel<Player>> allPlayers() {
        var players = characterService.getAllPlayers().stream().map(playerModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(players, linkTo(methodOn(CharacterController.class).allPlayers()).withSelfRel());
    }

    @GetMapping("/npcs")
    public CollectionModel<EntityModel<NPC>> allNpcs() {
        var npcs = characterService.getAllNPCs().stream().map(npcModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(npcs, linkTo(methodOn(CharacterController.class).allNpcs()).withSelfRel());
    }

    @GetMapping("/players/{id}")
    public EntityModel<Player> getPlayerById(@PathVariable Long id) {
        Player player = characterService.getPlayerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/npcs/{id}")
    public EntityModel<NPC> getNpcById(@PathVariable Long id) {
        NPC npc = characterService.getNPCById(id)
                .orElseThrow(() -> new IllegalArgumentException("NPC does not exist."));

        return npcModelAssembler.toModel(npc);
    }

    @DeleteMapping("/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        characterService.deletePlayerById(id);

        log.info(String.format("Player deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/players/{id}/purchase_usable_tool/{tool_id}")
    public EntityModel<Player> tryToBuyUsableTool(@PathVariable Long id, @PathVariable Long tool_id) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Tool tool = toolService.getUsableToolById(tool_id).orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        characterService.tryToBuyTool(player, tool);

        log.info(String.format("Player with ID %s tried to purchase tool with ID %s.", player.getId(), tool.getId()));

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/purchase_non_usable_tool/{tool_id}")
    public EntityModel<Player> tryToBuyNonUsableTool(@PathVariable Long id, @PathVariable Long tool_id) { //TODO: értem a miértjét, de camelCase legyen a
        // változó, a Spring át tudja konvertálni alpaból, de ha nem akkor a @PathVariable-nek meglehet adni a tényleges mappinget.
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Tool tool = toolService.getNonUsableToolById(tool_id).orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        characterService.tryToBuyTool(player, tool);

        log.info(String.format("Player with ID %s tried to purchase tool with ID %s.", player.getId(), tool.getId()));

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/learn_technique/{technique_id}")
    public EntityModel<Player> tryToLearnTechnique(@PathVariable Long id, @PathVariable Long technique_id) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Technique technique = techniqueService.getById(technique_id).orElseThrow(() -> new IllegalArgumentException("Technique does not exist."));

        characterService.tryToLearnTechnique(player, technique);

        log.info(String.format("Player with ID %s tried to learn technique with ID %s.", player.getId(), technique.getId()));

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/take_action/{action_id}")
    public EntityModel<Player> tryToTakeStatelessAction(@PathVariable Long id, @PathVariable Long action_id) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));
        Action action = actionService.getStatelessActionById(action_id)
                .orElseThrow(() -> new IllegalArgumentException("No such stateless action exists."));

        taskService.tryToScheduleActionForPlayer(player, action);

        log.info(String.format("Player with ID %s tried to take action with ID %s.", player.getId(), action.getId()));

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/duel/{duel_id}/{opponent_id}")
    public EntityModel<Player> challengeToDuel(@PathVariable Long id, @PathVariable Long duel_id, @PathVariable Long opponent_id) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));
        Character opponent = characterService.findById(opponent_id).orElseThrow(() -> new IllegalArgumentException("Opponent does not exist."));
        DuelAction action = actionService.getDuelActionById(duel_id)
                .orElseThrow(() -> new IllegalArgumentException("No such duel action exists."));

        taskService.tryToScheduleDuelActionForPlayer(player, action, opponent);

        log.info(String.format("Player with ID %s tried to challenge character with ID %s to a duel.",
                player.getId(), opponent.getId()));

        return playerModelAssembler.toModel(player);
    }
}
