package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.NpcModelAssembler;
import org.github.turostaska.controller.assembler.PlayerModelAssembler;
import org.github.turostaska.domain.Player;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CharacterController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private ICharacterService characterService;
    @Autowired private IToolService toolService;
    @Autowired private PlayerModelAssembler playerModelAssembler;
    @Autowired private NpcModelAssembler npcModelAssembler;

    @GetMapping("/players")
    public List<Player> allPlayers() {
        return characterService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    public EntityModel<Player> getName(@PathVariable Long id) {
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

    @PutMapping("/players/{id}")
    ResponseEntity<?> replacePlayer(@RequestBody Player newPlayer, @PathVariable Long id) {
        if (characterService.getPlayerById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Player with id %s does not exist.", id));

        newPlayer.setId(id);
        characterService.addOrUpdate(newPlayer);
        //todo: a toolok id-ját szarul olvassa be for some reason?
        log.info(String.format("Player updated with id %s", id));

        EntityModel<Player> entityModel = playerModelAssembler.toModel(characterService.getPlayerById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
