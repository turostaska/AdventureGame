package org.github.turostaska.adventuregame.controller;

import org.github.turostaska.adventuregame.controller.assembler.NonUsableToolModelAssembler;
import org.github.turostaska.adventuregame.controller.assembler.UsableToolModelAssembler;
import org.github.turostaska.adventuregame.domain.NonUsableTool;
import org.github.turostaska.adventuregame.domain.UsableTool;
import org.github.turostaska.adventuregame.service.IToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ToolController {
    private static final Logger log = LoggerFactory.getLogger(ToolController.class);

    @Autowired private IToolService toolService;
    @Autowired private UsableToolModelAssembler usableToolModelAssembler;
    @Autowired private NonUsableToolModelAssembler nonUsableToolModelAssembler;

    @GetMapping("/usable_tools")
    public CollectionModel<EntityModel<UsableTool>> allUsableTools() {
        var tools = toolService.getAllUsableTools().stream().map(usableToolModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tools, linkTo(methodOn(ToolController.class).allUsableTools()).withSelfRel());
    }

    @GetMapping("/non_usable_tools")
    public CollectionModel<EntityModel<NonUsableTool>> allNonUsableTools() {
        var tools = toolService.getAllNonUsableTools().stream().map(nonUsableToolModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tools, linkTo(methodOn(ToolController.class).allNonUsableTools()).withSelfRel());
    }

    @GetMapping("/usable_tools/{id}")
    public EntityModel<UsableTool> getUsableTool(@PathVariable Long id) {
        UsableTool tool = toolService.getUsableToolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        return usableToolModelAssembler.toModel(tool);
    }

    @GetMapping("/non_usable_tools/{id}")
    public EntityModel<NonUsableTool> getNonUsableTool(@PathVariable Long id) {
        NonUsableTool tool = toolService.getNonUsableToolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        return nonUsableToolModelAssembler.toModel(tool);
    }

    @PostMapping("/usable_tools")
    ResponseEntity<?> addUsableTool(@RequestBody UsableTool newTool) {
        newTool.setId(null);
        toolService.addOrUpdate(newTool);

        UsableTool tool = toolService.getUsableToolByName(newTool.getName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add tool " + newTool.getName() + "."));

        log.info("Created tool with name '{}' and ID {}", tool.getName(), tool.getId());

        var entityModel = usableToolModelAssembler.toModel(tool);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/non_usable_tools")
    ResponseEntity<?> addNonUsableTool(@RequestBody NonUsableTool newTool) {
        newTool.setId(null);
        toolService.addOrUpdate(newTool);

        NonUsableTool tool = toolService.getNonUsableToolByName(newTool.getName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add tool " + newTool.getName() + "."));

        log.info("Created tool with name '{}' and ID {}", tool.getName(), tool.getId());

        var entityModel = nonUsableToolModelAssembler.toModel(tool);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/usable_tools/{id}")
    ResponseEntity<?> deleteUsableTool(@PathVariable Long id) {
        toolService.deleteUsableToolById(id);

        log.info("Tool deleted with id {}", id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/non_usable_tools/{id}")
    ResponseEntity<?> deleteNonUsableTool(@PathVariable Long id) {
        toolService.deleteNonUsableToolById(id);

        log.info("Tool deleted with id {}", id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usable_tools/{id}")
    ResponseEntity<?> replaceUsableTool(@RequestBody UsableTool newTool, @PathVariable Long id) {
        if (toolService.getUsableToolById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Tool with id %s does not exist.", id));

        newTool.setId(id);
        toolService.addOrUpdate(newTool);

        log.info("Tool updated with id {}", id);

        var entityModel = usableToolModelAssembler.toModel(toolService.getUsableToolById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/non_usable_tools/{id}")
    ResponseEntity<?> replaceNonUsableTool(@RequestBody NonUsableTool newTool, @PathVariable Long id) {
        if (toolService.getNonUsableToolById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Tool with id %s does not exist.", id));

        newTool.setId(id);
        toolService.addOrUpdate(newTool);

        log.info("Tool updated with id {}", id);

        var entityModel = nonUsableToolModelAssembler.toModel(toolService.getNonUsableToolById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }



}
