package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.AdventureActionModelAssembler;
import org.github.turostaska.controller.assembler.MissionActionModelAssembler;
import org.github.turostaska.controller.assembler.RestActionModelAssembler;
import org.github.turostaska.domain.*;
import org.github.turostaska.service.IActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActionController {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private IActionService actionService;
    @Autowired private RestActionModelAssembler restActionModelAssembler;
    @Autowired private MissionActionModelAssembler missionActionModelAssembler;
    @Autowired private AdventureActionModelAssembler adventureActionModelAssembler;

    @GetMapping("/rest_actions")
    public CollectionModel<EntityModel<RestAction>> allRestActions() {
        var actions = actionService.getAllRestActions().stream().map(restActionModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actions, linkTo(methodOn(ActionController.class).allRestActions()).withSelfRel());
    }

    @GetMapping("/adventure_actions")
    public CollectionModel<EntityModel<AdventureAction>> allAdventureActions() {
        var actions = actionService.getAllAdventureActions().stream().map(adventureActionModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actions, linkTo(methodOn(ActionController.class).allAdventureActions()).withSelfRel());
    }

    @GetMapping("/mission_actions")
    public CollectionModel<EntityModel<MissionAction>> allMissionActions() {
        var actions = actionService.getAllMissionActions().stream().map(missionActionModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actions, linkTo(methodOn(ActionController.class).allMissionActions()).withSelfRel());
    }

    @PostMapping("/rest_actions")
    ResponseEntity<?> addRestAction(@RequestBody RestAction newAction) {
        newAction.setId(null);

        RestAction action = (RestAction) actionService.addOrUpdate(newAction);

        log.info(String.format("Created rest action with ID %s", action.getId()));

        var entityModel = restActionModelAssembler.toModel(action);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/rest_actions/{id}")
    public EntityModel<RestAction> getRestAction(@PathVariable Long id) {
        RestAction action = (RestAction) actionService.getById(id).orElseThrow(() -> new IllegalArgumentException("Action does not exist."));

        return restActionModelAssembler.toModel(action);
    }

    @GetMapping("/adventure_actions/{id}")
    public EntityModel<AdventureAction> getAdventureAction(@PathVariable Long id) {
        AdventureAction action = (AdventureAction) actionService.getById(id).orElseThrow(() -> new IllegalArgumentException("Action does not exist."));

        return adventureActionModelAssembler.toModel(action);
    }

    @GetMapping("/mission_actions/{id}")
    public EntityModel<MissionAction> getMissionAction(@PathVariable Long id) {
        MissionAction action = (MissionAction) actionService.getById(id).orElseThrow(() -> new IllegalArgumentException("Action does not exist."));

        return missionActionModelAssembler.toModel(action);
    }

    @PostMapping("/adventure_actions")
    ResponseEntity<?> addAdventureAction(@RequestBody AdventureAction newAction) {
        newAction.setId(null);

        AdventureAction action = (AdventureAction) actionService.addOrUpdate(newAction);

        log.info(String.format("Created adventure action with ID %s", action.getId()));

        var entityModel = adventureActionModelAssembler.toModel(action);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/mission_actions")
    ResponseEntity<?> addMissionAction(@RequestBody MissionAction newAction) {
        newAction.setId(null);

        MissionAction action = (MissionAction) actionService.addOrUpdate(newAction);

        log.info(String.format("Created mission action with ID %s", action.getId()));

        var entityModel = missionActionModelAssembler.toModel(action);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping({"/adventure_actions", "/rest_actions", "/mission_actions"})
    ResponseEntity<?> delete(@PathVariable Long id) {
        actionService.deleteById(id);

        log.info(String.format("Action deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/adventure_actions/{id}", "/rest_actions/{id}", "/mission_actions/{id}"})
    ResponseEntity<?> replaceRestAction(@RequestBody Action newAction, @PathVariable Long id) {
        if (actionService.getById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Action with id %s does not exist.", id));

        newAction.setId(id);
        actionService.addOrUpdate(newAction);

        log.info(String.format("Action updated with id %s", id));

        var entityModel = restActionModelAssembler.toModel((RestAction) actionService.getById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
