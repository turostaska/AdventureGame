package org.github.turostaska.adventuregame.controller.assembler;

import org.github.turostaska.adventuregame.controller.ToolController;
import org.github.turostaska.adventuregame.domain.UsableTool;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UsableToolModelAssembler implements RepresentationModelAssembler<UsableTool, EntityModel<UsableTool>> {

    @Override
    public EntityModel<UsableTool> toModel(UsableTool entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ToolController.class).getUsableTool(entity.getId())).withSelfRel(),
                linkTo(methodOn(ToolController.class).allUsableTools()).withRel("usable_tools"));
    }
}
