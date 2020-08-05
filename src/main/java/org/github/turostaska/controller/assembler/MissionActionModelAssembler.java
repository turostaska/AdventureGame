package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.ActionController;
import org.github.turostaska.domain.MissionAction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class MissionActionModelAssembler implements RepresentationModelAssembler<MissionAction, EntityModel<MissionAction>> {
    @Override
    public EntityModel<MissionAction> toModel(MissionAction entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ActionController.class).getMissionAction(entity.getId())).withSelfRel(),
                linkTo(methodOn(ActionController.class).allMissionActions()).withRel("mission_actions"));
    }
}
