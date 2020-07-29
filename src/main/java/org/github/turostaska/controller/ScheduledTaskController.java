package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.ScheduledTaskModelAssembler;
import org.github.turostaska.domain.RestAction;
import org.github.turostaska.domain.ScheduledTask;
import org.github.turostaska.domain.User;
import org.github.turostaska.service.IScheduledTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ScheduledTaskController {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired IScheduledTaskService taskService;
    @Autowired ScheduledTaskModelAssembler assembler;

    @GetMapping("/tasks")
    public CollectionModel<EntityModel<ScheduledTask>> all() {
        var tasks = taskService.getAll().stream().map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tasks, linkTo(methodOn(ScheduledTaskController.class).all()).withSelfRel());
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<ScheduledTask> get(@PathVariable Long id) {
        ScheduledTask task = taskService.getById(id).orElseThrow(() -> new IllegalArgumentException("Task does not exist."));

        return assembler.toModel(task);
    }

}
