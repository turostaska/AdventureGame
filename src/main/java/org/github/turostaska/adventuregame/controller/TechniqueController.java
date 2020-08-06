package org.github.turostaska.adventuregame.controller;

import org.github.turostaska.adventuregame.controller.assembler.TechniqueModelAssembler;
import org.github.turostaska.adventuregame.domain.Technique;
import org.github.turostaska.adventuregame.service.ITechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TechniqueController {
    private static final Logger log = LoggerFactory.getLogger(TechniqueController.class);

    @Autowired private ITechniqueService techniqueService;
    @Autowired private TechniqueModelAssembler assembler;

    @GetMapping("/techniques")
    public CollectionModel<EntityModel<Technique>> all() {
        var techniques = techniqueService.getAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(techniques, linkTo(methodOn(TechniqueController.class).all()).withSelfRel());
    }

    @GetMapping("/techniques/{id}")
    public EntityModel<Technique> getById(@PathVariable Long id) {
        Technique technique = techniqueService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Technique does not exist."));

        return assembler.toModel(technique);
    }

    @PostMapping("/techniques")
    ResponseEntity<?> add(@RequestBody Technique newTechnique) {
        newTechnique.setId(null);
        var technique = techniqueService.addOrUpdate(newTechnique);

        log.info("Created technqiue with name '{}' and ID {}", technique.getName(), technique.getId());

        EntityModel<Technique> entityModel = assembler.toModel(technique);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/techniques/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        techniqueService.deleteById(id);

        log.info("Technique deleted with id {}", id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/techniques/{id}")
    ResponseEntity<?> replace(@RequestBody Technique newTechnique, @PathVariable Long id) {
        if (techniqueService.getById(id).isEmpty()) {
            String message = String.format("Technique with id %s does not exist.", id);
            log.info(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        newTechnique.setId(id);
        techniqueService.addOrUpdate(newTechnique);

        log.info("Technique updated with id {}", id);

        EntityModel<Technique> entityModel = assembler.toModel(techniqueService.getById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


}
