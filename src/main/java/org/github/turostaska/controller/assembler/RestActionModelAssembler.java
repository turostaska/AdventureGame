package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.ActionController;
import org.github.turostaska.domain.RestAction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RestActionModelAssembler implements RepresentationModelAssembler<RestAction, EntityModel<RestAction>> {
    @Override
    public EntityModel<RestAction> toModel(RestAction entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ActionController.class).getRestAction(entity.getId())).withSelfRel(),
                linkTo(methodOn(ActionController.class).allRestActions()).withRel("rest_actions"));
    }
}