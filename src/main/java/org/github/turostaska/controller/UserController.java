package org.github.turostaska.controller;

import org.github.turostaska.config.ConfigurationClass;
import org.github.turostaska.controller.assembler.UserModelAssembler;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.User;
import org.github.turostaska.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Autowired private IUserService userService;
    @Autowired private UserModelAssembler assembler;

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        var users = userService.getAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> get(@PathVariable Long id) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        return assembler.toModel(user);
    }

    @PostMapping("/users")
    ResponseEntity<?> add(@RequestBody User newUser) {
        newUser.setId(null);
        userService.addOrUpdate(newUser);
        User user = userService.getByName(newUser.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't add user " + newUser.getUserName() + "."));

        log.info(String.format("Created user with name '%s' and ID %s", user.getUserName(), user.getId()));

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/users/{id}/register")
    public EntityModel<User> register(@PathVariable Long id) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        userService.createPlayerForUser(user);
        log.info(String.format("Created player with ID %s for user '%s'", user.getPlayer().getId(), user.getUserName()));

        return assembler.toModel(user);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteById(id);

        log.info(String.format("User deleted with id %s", id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> replace(@RequestBody User newUser, @PathVariable Long id) {
        if (userService.getById(id).isEmpty())
            throw new IllegalArgumentException(String.format("User with id %s does not exist.", id));

        newUser.setId(id);
        userService.addOrUpdate(newUser);

        log.info(String.format("User updated with id %s", id));

        EntityModel<User> entityModel = assembler.toModel(userService.getById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
