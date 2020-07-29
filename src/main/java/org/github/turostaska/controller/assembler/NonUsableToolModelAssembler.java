package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.ToolController;
import org.github.turostaska.domain.NonUsableTool;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class NonUsableToolModelAssembler implements RepresentationModelAssembler<NonUsableTool, EntityModel<NonUsableTool>> {

    @Override
    public EntityModel<NonUsableTool> toModel(NonUsableTool entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ToolController.class).getNonUsableTool(entity.getId())).withSelfRel(),
                linkTo(methodOn(ToolController.class).allNonUsableTools()).withRel("non_usable_tools"));
    }
}