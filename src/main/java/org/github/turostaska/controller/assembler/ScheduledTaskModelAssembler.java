package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.ScheduledTaskController;
import org.github.turostaska.domain.ScheduledTask;
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
