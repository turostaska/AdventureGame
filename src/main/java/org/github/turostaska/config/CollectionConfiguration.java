package org.github.turostaska.config;

import org.github.turostaska.dao.*;
import org.github.turostaska.dao.impl.list.*;
import org.github.turostaska.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.github.turostaska.service.*;
import org.github.turostaska.service.impl.collection.*;

@Configuration
public class CollectionConfiguration {
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
    public ICharacterService characterService(INPCDao npcDao, IPlayerDao playerDao, IActionService actionService) {
        return new CollectionCharacterService(playerDao, npcDao);
    }

    @Bean
    public ITechniqueService techniqueService(ITechniqueDao techniqueDao) {
        return new CollectionTechniqueService(techniqueDao);
    }

    @Bean
    public IToolService toolService(IToolDao toolDao) {
        return new CollectionToolService(toolDao);
    }

    @Bean
    public IUserService userService(IUserDao userDao) {
        return new CollectionUserService(userDao);
    }

    @Bean
    public IScheduledActionService scheduledActionService(IScheduledActionDao scheduledActionDao) {
        return new CollectionScheduledActionService(scheduledActionDao);
    }
}
