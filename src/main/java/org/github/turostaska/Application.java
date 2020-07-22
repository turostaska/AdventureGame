package org.github.turostaska;

import org.github.turostaska.config.CollectionConfiguration;
import org.github.turostaska.dao.IUserDao;
import org.github.turostaska.domain.Player;
import org.github.turostaska.domain.User;
import org.github.turostaska.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.github.turostaska.service.impl.collection.CollectionCharacterService;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
