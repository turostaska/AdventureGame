package org.github.turostaska.adventuregame.repository;

import org.github.turostaska.adventuregame.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}
