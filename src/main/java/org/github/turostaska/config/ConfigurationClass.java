package org.github.turostaska.config;

import org.github.turostaska.dao.*;
import org.github.turostaska.dao.impl.list.*;
import org.github.turostaska.service.impl.repository.RepositoryCharacterService;
import org.github.turostaska.service.impl.repository.RepositoryScheduledTaskService;
import org.github.turostaska.service.impl.repository.RepositoryToolService;
import org.github.turostaska.service.impl.repository.RepositoryUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.github.turostaska.service.*;
import org.github.turostaska.service.impl.collection.*;
import org.springframework.context.annotation.Primary;

@org.springframework.context.annotation.Configuration
public class ConfigurationClass {
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
}
