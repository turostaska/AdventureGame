package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.TechniqueModelAssembler;
import org.github.turostaska.domain.Technique;
import org.github.turostaska.service.ITechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TechniqueController {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private ITechniqueService techniqueService;
    @Autowired private TechniqueModelAssembler assembler;

    @GetMapping("/techniques")
    public List<Technique> all() {
        return techniqueService.getAll();
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
        techniqueService.addOrUpdate(newTechnique);

        Technique technique = techniqueService.getByName(newTechnique.getName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add technique " + newTechnique.getName() + "."));

        log.info(String.format("Created technqiue with name '%s' and ID %s", technique.getName(), technique.getId()));

        EntityModel<Technique> entityModel = assembler.toModel(technique);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/techniques/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        techniqueService.deleteById(id);

        log.info(String.format("Technique deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/techniques/{id}")
    ResponseEntity<?> replace(@RequestBody Technique newTechnique, @PathVariable Long id) {
        if (techniqueService.getById(id).isEmpty())
            throw new IllegalArgumentException(String.format("Technique with id %s does not exist.", id));

        newTechnique.setId(id);
        techniqueService.addOrUpdate(newTechnique);

        log.info(String.format("Technique updated with id %s", id));

        EntityModel<Technique> entityModel = assembler.toModel(techniqueService.getById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


}
