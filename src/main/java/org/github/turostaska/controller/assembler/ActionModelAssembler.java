package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.UserController;
import org.github.turostaska.domain.Action;
import org.github.turostaska.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ActionModelAssembler implements RepresentationModelAssembler<Action, EntityModel<Action>> {
    @Override
    public EntityModel<Action> toModel(Action entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).get(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}