package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.UserController;
import org.github.turostaska.domain.NPC;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class NpcModelAssembler implements RepresentationModelAssembler<NPC, EntityModel<NPC>> {

    @Override
    public EntityModel<NPC> toModel(NPC entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).get(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}