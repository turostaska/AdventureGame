package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.NpcModelAssembler;
import org.github.turostaska.controller.assembler.PlayerModelAssembler;
import org.github.turostaska.domain.*;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.ITechniqueService;
import org.github.turostaska.service.IToolService;
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
    @Autowired private PlayerModelAssembler playerModelAssembler;
    @Autowired private NpcModelAssembler npcModelAssembler;

    @GetMapping("/players")
    public CollectionModel<EntityModel<Player>> allPlayers() {
        var players = characterService.getAllPlayers().stream().map(playerModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(players, linkTo(methodOn(CharacterController.class).allPlayers()).withSelfRel());
    }

    @GetMapping("/players/{id}")
    public EntityModel<Player> getPlayerById(@PathVariable Long id) {
        Player player = characterService.getPlayerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player does not exist."));

        return playerModelAssembler.toModel(player);
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
    public EntityModel<Player> tryToBuyNonUsableTool(@PathVariable Long id, @PathVariable Long tool_id) {
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

}
