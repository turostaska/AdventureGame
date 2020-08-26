package org.github.turostaska.adventuregame.config;

import org.github.turostaska.adventuregame.dao.*;
import org.github.turostaska.adventuregame.dao.impl.list.*;
import org.github.turostaska.adventuregame.service.*;
import org.github.turostaska.adventuregame.service.impl.collection.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("inmemory")
public class InMemoryConfiguration {
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
    public IActionService inMemoryActionService(IActionDao actionDao) {
        return new CollectionActionService(actionDao);
    }

    @Bean
    public ICharacterService inMemoryCharacterService(INPCDao npcDao, IPlayerDao playerDao, IActionService actionService) {
        return new CollectionCharacterService(playerDao, npcDao);
    }

    @Bean
    public ITechniqueService inMemoryTechniqueService(ITechniqueDao techniqueDao) {
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

}
