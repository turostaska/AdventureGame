package org.github.turostaska.repository;

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
    @Autowired
    private TestEntityManager em;

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void addingNewUser() {
        User faci = new User("fáci", "AmyGlassires99", "faci@lmente.hu");

        em.persist(faci);
        em.flush();

        Optional<User> found = userRepository.findByUserName("fáci");

        assertEquals(found.get().getUserName(), "fáci");
    }

}