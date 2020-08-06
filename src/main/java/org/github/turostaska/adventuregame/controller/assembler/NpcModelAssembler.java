package org.github.turostaska.adventuregame.controller.assembler;

import org.github.turostaska.adventuregame.controller.CharacterController;
import org.github.turostaska.adventuregame.domain.NPC;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class NpcModelAssembler implements RepresentationModelAssembler<NPC, EntityModel<NPC>> {

    @Override
    public EntityModel<NPC> toModel(NPC entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(CharacterController.class).getNpcById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CharacterController.class).allNpcs()).withRel("npcs"));
    }
}
