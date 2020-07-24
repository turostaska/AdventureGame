package org.github.turostaska.controller;

import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.User;
import org.github.turostaska.service.ICharacterService;
import org.github.turostaska.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired private IUserService userService;
    @Autowired private ICharacterService characterService;

    @GetMapping("/player")
    public Player getPlayer(@RequestParam(value = "name", defaultValue = "fáci") String name ) {
        User user = new User(name, "Kutya12345", name + "@cringomail.hu");
        user.createPlayer();
        return new Player();
    }

    @GetMapping("/players")
    public List<Player> allPlayers() {
        return characterService.getAllPlayers();
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id).orElseThrow(() -> new IllegalArgumentException("User does not exist."));
    }

    @PostMapping("/users")
    User addUser(@RequestBody User newUser) {
        userService.addOrUpdate(newUser);
        User user = userService.getByName(newUser.getUserName()).get();
        userService.createPlayerForUser(user);
        return user;
    }

}
