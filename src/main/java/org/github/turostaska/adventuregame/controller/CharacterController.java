package org.github.turostaska.adventuregame.controller;

import org.github.turostaska.adventuregame.controller.assembler.NpcModelAssembler;
import org.github.turostaska.adventuregame.controller.assembler.PlayerModelAssembler;
import org.github.turostaska.adventuregame.domain.Character;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);

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

        log.info("Player deleted with id {}", id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/players/{id}/purchase_usable_tool/{toolId}")
    public EntityModel<Player> tryToBuyUsableTool(@PathVariable Long id, @PathVariable Long toolId) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Tool tool = toolService.getUsableToolById(toolId).orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        characterService.tryToBuyTool(player, tool);

        log.info("Player with ID {} tried to purchase tool with ID {}.", player.getId(), tool.getId());

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/purchase_non_usable_tool/{toolId}")
    public EntityModel<Player> tryToBuyNonUsableTool(@PathVariable Long id, @PathVariable Long toolId) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Tool tool = toolService.getNonUsableToolById(toolId).orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        characterService.tryToBuyTool(player, tool);

        log.info("Player with ID {} tried to purchase tool with ID {}.", player.getId(), tool.getId());

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/learn_technique/{techniqueId}")
    public EntityModel<Player> tryToLearnTechnique(@PathVariable Long id, @PathVariable Long techniqueId) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        Technique technique = techniqueService.getById(techniqueId).orElseThrow(() -> new IllegalArgumentException("Technique does not exist."));

        characterService.tryToLearnTechnique(player, technique);

        log.info("Player with ID {} tried to learn technique with ID {}.", player.getId(), technique.getId());

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/take_action/{actionId}")
    public EntityModel<Player> tryToTakeStatelessAction(@PathVariable Long id, @PathVariable Long actionId) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));
        Action action = actionService.getStatelessActionById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("No such stateless action exists."));

        taskService.tryToScheduleActionForPlayer(player, action);

        log.info("Player with ID {} tried to take action with ID {}.", player.getId(), action.getId());

        return playerModelAssembler.toModel(player);
    }

    @GetMapping("/players/{id}/duel/{duelId}/{opponentId}")
    public EntityModel<Player> challengeToDuel(@PathVariable Long id, @PathVariable Long duelId, @PathVariable Long opponentId) {
        Player player = characterService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player does not exist."));
        Character opponent = characterService.findById(opponentId).orElseThrow(() -> new IllegalArgumentException("Opponent does not exist."));
        DuelAction action = actionService.getDuelActionById(duelId)
                .orElseThrow(() -> new IllegalArgumentException("No such duel action exists."));

        taskService.tryToScheduleDuelActionForPlayer(player, action, opponent);

        log.info("Player with ID {} tried to challenge character with ID {} to a duel.",
                player.getId(), opponent.getId());

        return playerModelAssembler.toModel(player);
    }
}
