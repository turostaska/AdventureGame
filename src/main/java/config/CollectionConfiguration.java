package config;

import dao.*;
import dao.impl.list.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.*;
import service.impl.*;

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
        return new ActionService(actionDao);
    }

    @Bean
    public ICharacterService characterService(INPCDao npcDao, IPlayerDao playerDao, IActionService actionService) {
        return new CharacterService(playerDao, npcDao);
    }

    @Bean
    public ITechniqueService techniqueService(ITechniqueDao techniqueDao) {
        return new TechniqueService(techniqueDao);
    }

    @Bean
    public IToolService toolService(IToolDao toolDao) {
        return new ToolService(toolDao);
    }

    @Bean
    public IUserService userService(IUserDao userDao) {
        return new UserService(userDao);
    }

    @Bean
    public IScheduledActionService scheduledActionService(IScheduledActionDao scheduledActionDao) {
        return new ScheduledActionService(scheduledActionDao);
    }
}
