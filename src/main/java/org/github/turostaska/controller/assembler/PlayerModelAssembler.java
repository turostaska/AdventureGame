package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.CharacterController;
import org.github.turostaska.domain.Player;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>> {

    @Override
    public EntityModel<Player> toModel(Player entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(CharacterController.class).getPlayerById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CharacterController.class).allPlayers()).withRel("players"));
    }
}
