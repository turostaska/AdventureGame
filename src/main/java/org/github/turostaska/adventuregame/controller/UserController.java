package org.github.turostaska.adventuregame.controller;

import org.github.turostaska.adventuregame.controller.assembler.UserModelAssembler;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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

    @PostMapping("/registration")
    ResponseEntity<?> add(@RequestBody User newUser) {
        User user;
        try {
            user = userService.tryToRegister(newUser.getUserName(), newUser.getPassword(), newUser.getEmail());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        log.info("Created user with name '{}' and ID {}", user.getUserName(), user.getId());

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody User credentials) {
        User user;
        try {
            user = userService.tryToLogIn(credentials.getUserName(), credentials.getPassword());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        log.info("User with name '{}' and ID {} has successfully logged in.", user.getUserName(), user.getId());

        //todo: jogosultságok beállítása?

        EntityModel<User> entityModel = assembler.toModel(user);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }

    @GetMapping("/users/{id}/create_player")
    public EntityModel<User> createPlayerForUser(@PathVariable Long id) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        userService.createPlayerForUser(user);
        log.info("Created player with ID {} for user '{}'", user.getPlayer().getId(), user.getUserName());

        return assembler.toModel(user);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteById(id);

        log.info("User deleted with id {}", id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> replace(@RequestBody User newUser, @PathVariable Long id) {
        if (userService.getById(id).isEmpty()) {
            String message = String.format("User with id %s does not exist.", id);
            log.info(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        newUser.setId(id);
        userService.addOrUpdate(newUser);

        log.info("User updated with id {}", id);

        EntityModel<User> entityModel = assembler.toModel(userService.getById(id).get());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
