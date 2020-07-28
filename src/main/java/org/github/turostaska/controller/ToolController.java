package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.ToolModelAssembler;
import org.github.turostaska.domain.*;
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
public class ToolController {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private IToolService toolService;
    @Autowired private ToolModelAssembler assembler;

    @GetMapping("/usable_tools")
    public List<UsableTool> allUsableTools() {
        return toolService.getAllUsableTools();
    }

    @GetMapping("/non_usable_tools")
    public List<NonUsableTool> allNonUsableTools() {
        return toolService.getAllNonUsableTools();
    }

    @GetMapping("/usable_tools/{id}")
    public EntityModel<Tool> getUsableTool(@PathVariable Long id) {
        UsableTool tool = toolService.getUsableToolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        return assembler.toModel(tool);
    }

    @GetMapping("/non_usable_tools/{id}")
    public EntityModel<Tool> getNonUsableTool(@PathVariable Long id) {
        NonUsableTool tool = toolService.getNonUsableToolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tool does not exist."));

        return assembler.toModel(tool);
    }

    @PostMapping("/usable_tools")
    ResponseEntity<?> addUsableTool(@RequestBody UsableTool newTool) {
        newTool.setId(null);
        toolService.addOrUpdate(newTool);

        UsableTool tool = toolService.getUsableToolByName(newTool.getName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add tool " + newTool.getName() + "."));

        log.info(String.format("Created tool with name '%s' and ID %s", tool.getName(), tool.getId()));

        EntityModel<Tool> entityModel = assembler.toModel(tool);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/non_usable_tools")
    ResponseEntity<?> addNonUsableTool(@RequestBody NonUsableTool newTool) {
        newTool.setId(null);
        toolService.addOrUpdate(newTool);

        NonUsableTool tool = toolService.getNonUsableToolByName(newTool.getName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add tool " + newTool.getName() + "."));

        log.info(String.format("Created tool with name '%s' and ID %s", tool.getName(), tool.getId()));

        EntityModel<Tool> entityModel = assembler.toModel(tool);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/usable_tools/{id}")
    ResponseEntity<?> deleteUsableTool(@PathVariable Long id) {
        toolService.deleteUsableToolById(id);

        log.info(String.format("Tool deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/non_usable_tools/{id}")
    ResponseEntity<?> deleteNonUsableTool(@PathVariable Long id) {
        toolService.deleteNonUsableToolById(id);

        log.info(String.format("Tool deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usable_tools/{id}")
    ResponseEntity<?> replaceUsableTool(@RequestBody UsableTool newTool, @PathVariable Long id) {
        if (toolService.getUsableToolById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Tool with id %s does not exist.", id));

        newTool.setId(id);
        toolService.addOrUpdate(newTool);

        log.info(String.format("Tool updated with id %s", id));

        EntityModel<Tool> entityModel = assembler.toModel(toolService.getUsableToolById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/non_usable_tools/{id}")
    ResponseEntity<?> replaceNonUsableTool(@RequestBody NonUsableTool newTool, @PathVariable Long id) {
        if (toolService.getNonUsableToolById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Tool with id %s does not exist.", id));

        newTool.setId(id);
        toolService.addOrUpdate(newTool);

        log.info(String.format("Tool updated with id %s", id));

        EntityModel<Tool> entityModel = assembler.toModel(toolService.getNonUsableToolById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }



}
