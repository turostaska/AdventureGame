package org.github.turostaska.config;

import org.github.turostaska.controller.assembler.NpcModelAssembler;
import org.github.turostaska.controller.assembler.PlayerModelAssembler;
import org.github.turostaska.controller.assembler.UserModelAssembler;
import org.github.turostaska.dao.*;
import org.github.turostaska.dao.impl.list.*;
import org.github.turostaska.domain.*;
import org.github.turostaska.service.impl.repository.RepositoryCharacterService;
import org.github.turostaska.service.impl.repository.RepositoryScheduledTaskService;
import org.github.turostaska.service.impl.repository.RepositoryToolService;
import org.github.turostaska.service.impl.repository.RepositoryUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.github.turostaska.service.*;
import org.github.turostaska.service.impl.collection.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class ConfigurationClass {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationClass.class);

    @Bean
    public IActionDao actionDao() {
        return new CollectionActionDao();
    }

    @Bean
    public IPlayerDao playerDao() {
        return new CollectionPlayerDao();
    }

    @Bean
    public INPCDao npcDao() {
        return new CollectionNPCDao();
    }

    @Bean
    public ITechniqueDao techniqueDao() {
        return new CollectionTechniqueDao();
    }

    @Bean
    public IToolDao toolDao() {
        return new CollectionToolDao();
    }

    @Bean
    public IUserDao userDao() {
        return new CollectionUserDao();
    }

    @Bean
    public IScheduledActionDao scheduledActionDao() {
        return new CollectionScheduledActionDao();
    }

    @Bean
    public IActionService actionService(IActionDao actionDao) {
        return new CollectionActionService(actionDao);
    }

    @Bean
    public ICharacterService inMemoryCharacterService(INPCDao npcDao, IPlayerDao playerDao, IActionService actionService) {
        return new CollectionCharacterService(playerDao, npcDao);
    }

    @Bean
    public ITechniqueService techniqueService(ITechniqueDao techniqueDao) {
        return new CollectionTechniqueService(techniqueDao);
    }

    @Bean
    public IToolService inMemoryToolService(IToolDao toolDao) {
        return new CollectionToolService(toolDao);
    }

    @Bean
    public IUserService inMemoryUserService(IUserDao userDao) {
        return new CollectionUserService(userDao);
    }

    @Bean
    public IScheduledTaskService inMemoryScheduledActionService(IScheduledActionDao scheduledActionDao) {
        return new CollectionScheduledTaskService(scheduledActionDao);
    }

    @Bean
    @Primary
    public IScheduledTaskService scheduledTaskService() {
        return new RepositoryScheduledTaskService();
    }

    @Bean
    @Primary
    public ICharacterService characterService() {
        return new RepositoryCharacterService();
    }

    @Bean
    @Primary
    public IToolService toolService() { return new RepositoryToolService(); }

    @Bean
    @Primary
    public IUserService userService() { return new RepositoryUserService(); }

    @Bean
    CommandLineRunner initUserDatabase(IUserService userService) {
        return args -> {
            log.info("Preloading...");

            List<User> users = List.of(
                    new User("fáci", "AmyGlassires99", "faci@lmente.hu"),
                    new User("rolcsi", "DamanciaTV7", "rolcsi@cringemail.hu")
                );
            users.forEach(userService::createPlayerForUser);
            users.forEach(userService::addOrUpdate);

            var tool = new UsableTool("dobócsillag", 5, 0, 400, 20);

            toolService().addOrUpdate(tool);

            characterService().tryToBuyTool(users.get(0).getPlayer(), tool);
            users.forEach(userService::addOrUpdate);


            log.info("Preloading finished.");
        };
    }

    @Bean UserModelAssembler userModelAssembler() { return new UserModelAssembler(); }

    @Bean PlayerModelAssembler characterModelAssembler() { return new PlayerModelAssembler(); }

    @Bean NpcModelAssembler npcModelAssembler() { return new NpcModelAssembler(); }
}
