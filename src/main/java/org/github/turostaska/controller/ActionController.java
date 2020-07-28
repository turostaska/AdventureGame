package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.ActionModelAssembler;
import org.github.turostaska.domain.AdventureAction;
import org.github.turostaska.domain.MissionAction;
import org.github.turostaska.domain.RestAction;
import org.github.turostaska.domain.Technique;
import org.github.turostaska.service.IActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActionController {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private IActionService actionService;
    @Autowired private ActionModelAssembler assembler;

    @GetMapping("/rest_actions")
    public List<RestAction> allRestActions() {
        return actionService.getAllRestActions();
    }

    @GetMapping("/adventure_actions")
    public List<AdventureAction> allAdventureActions() {
        return actionService.getAllAdventureActions();
    }

    @GetMapping("/mission_actions")
    public List<MissionAction> allMissionActions() {
        return actionService.getAllMissionActions();
    }

}
