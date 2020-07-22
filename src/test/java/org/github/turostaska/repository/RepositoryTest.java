package org.github.turostaska.repository;

import org.github.turostaska.domain.NonUsableTool;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.UsableTool;
import org.github.turostaska.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class RepositoryTest {
    @Autowired private TestEntityManager em;
    @Autowired private IUserRepository userRepository;
    @Autowired private IPlayerRepository playerRepository;
    @Autowired private IUsableToolRepository toolRepository;

    @Test
    public void addingNewUser() {
        User faci =   new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        User rolcsi = new User("rolcsi", "DamanciaTV7", "rolcsi@freemail.hu");

        em.persist(faci);
        em.persist(rolcsi);
        em.flush();

        Optional<User> found1 = userRepository.findByUserName("fáci");
        Optional<User> found2 = userRepository.findByUserName("rolcsi");

        assertEquals(found1.get().getUserName(), "fáci");
        assertEquals(found2.get().getUserName(), "rolcsi");
    }

    @Test
    public void registerNewPlayer() {
        User faci = new User("fáci", "AmyGlassires99", "faci@lmente.hu");
        Player player = new Player(faci);
        player.setID(null);

        em.persist(faci);
        em.flush();

        em.persist(player);
        em.flush();

        Optional<Player> found = playerRepository.findByName("fáci");

        assertEquals(found.get().getName(), "fáci");
    }

    //@Test
    public void getAllToolsByType() {
        UsableTool usableTool       = new UsableTool("dobócsillag", 5, 0, 400, 20);
        NonUsableTool nonUsableTool = new NonUsableTool("kard", 10, 0, 400, 2);

        em.persist(usableTool);
        em.persist(nonUsableTool);
        em.flush();

        //var usableTools = toolRepository.getAll(  );
    }
}