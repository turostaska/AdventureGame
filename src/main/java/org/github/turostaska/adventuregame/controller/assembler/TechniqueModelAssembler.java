package org.github.turostaska.adventuregame.controller.assembler;

import org.github.turostaska.adventuregame.controller.TechniqueController;
import org.github.turostaska.adventuregame.domain.Technique;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TechniqueModelAssembler  implements RepresentationModelAssembler<Technique, EntityModel<Technique>> {

    @Override
    public EntityModel<Technique> toModel(Technique entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(TechniqueController.class).getById(entity.getId())).withSelfRel(),
                linkTo(methodOn(TechniqueController.class).all()).withRel("techniques"));
    }
}