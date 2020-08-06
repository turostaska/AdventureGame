package org.github.turostaska.adventuregame.controller.assembler;

import org.github.turostaska.adventuregame.controller.ScheduledTaskController;
import org.github.turostaska.adventuregame.domain.ScheduledTask;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ScheduledTaskModelAssembler implements RepresentationModelAssembler<ScheduledTask, EntityModel<ScheduledTask>> {
    @Override
    public EntityModel<ScheduledTask> toModel(ScheduledTask entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ScheduledTaskController.class).get(entity.getId())).withSelfRel(),
                linkTo(methodOn(ScheduledTaskController.class).all()).withRel("tasks"));
    }
}
