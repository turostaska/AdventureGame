package org.github.turostaska.repository;

import org.github.turostaska.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    //todo: idk ez jó-e így
    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}
