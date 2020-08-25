package org.github.turostaska.adventuregame.config;

import org.github.turostaska.adventuregame.controller.assembler.*;
import org.github.turostaska.adventuregame.dao.*;
import org.github.turostaska.adventuregame.dao.impl.list.*;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.*;
import org.github.turostaska.adventuregame.service.impl.collection.*;
import org.github.turostaska.adventuregame.service.impl.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("inmemory")
    public IActionDao actionDao() {
        return new CollectionActionDao();
    }

    @Bean @Profile("inmemory")
    public IPlayerDao playerDao() {
        return new CollectionPlayerDao();
    }

    @Bean @Profile("inmemory")
    public INPCDao npcDao() {
        return new CollectionNPCDao();
    }

    @Bean @Profile("inmemory")
    public ITechniqueDao techniqueDao() {
        return new CollectionTechniqueDao();
    }

    @Bean @Profile("inmemory")
    public IToolDao toolDao() {
        return new CollectionToolDao();
    }

    @Bean @Profile("inmemory")
    public IUserDao userDao() {
        return new CollectionUserDao();
    }

    @Bean @Profile("inmemory")
    public IScheduledActionDao scheduledActionDao() {
        return new CollectionScheduledActionDao();
    }

    @Bean @Profile("inmemory")
    public IActionService inMemoryActionService(IActionDao actionDao) {
        return new CollectionActionService(actionDao);
    }

    @Bean @Profile("inmemory")
    public ICharacterService inMemoryCharacterService(INPCDao npcDao, IPlayerDao playerDao, IActionService actionService) {
        return new CollectionCharacterService(playerDao, npcDao);
    }

    @Bean @Profile("inmemory")
    public ITechniqueService inMemoryTechniqueService(ITechniqueDao techniqueDao) {
        return new CollectionTechniqueService(techniqueDao);
    }

    @Bean @Profile("inmemory")
    public IToolService inMemoryToolService(IToolDao toolDao) {
        return new CollectionToolService(toolDao);
    }

    @Bean @Profile("inmemory")
    public IUserService inMemoryUserService(IUserDao userDao) {
        return new CollectionUserService(userDao);
    }

    @Bean @Profile("inmemory")
    public IScheduledTaskService inMemoryScheduledActionService(IScheduledActionDao scheduledActionDao) {
        return new CollectionScheduledTaskService(scheduledActionDao);
    }

    @Bean @Profile("repository")
    public IScheduledTaskService scheduledTaskService() {
        return new RepositoryScheduledTaskService();
    }

    @Bean @Profile("repository")
    public ICharacterService characterService() {
        return new RepositoryCharacterService();
    }

    @Bean @Profile("repository")
    public IToolService toolService() { return new RepositoryToolService(); }

    @Bean @Profile("repository")
    public IUserService userService() { return new RepositoryUserService(); }

    @Bean @Profile("repository")
    public ITechniqueService techniqueService() {
        return new RepositoryTechniqueService();
    }

    @Bean @Profile("repository")
    public IActionService actionService() {
        return new RepositoryActionService();
    }

    @Bean
    CommandLineRunner initUserDatabase(IUserService userService) {
        return args -> {
            log.info("Preloading...");
            //preloadInitialDataForTesting();
//            AdventureAction action = new AdventureAction(10,
//                    List.of(techniqueService().getById(48L).orElseThrow(), techniqueService().getById(49L).orElseThrow()),
//                    List.of(toolService().getNonUsableToolById(46L).orElseThrow()), 0);
//            actionService().addOrUpdate(action);

            log.info("Preloading finished.");

            log.info("Rescheduling unfinished tasks...");
            scheduledTaskService().rescheduleUnfinishedTasksUponInitialization();
            log.info("Rescheduling finished.");

            
        };
    }

    private void preloadInitialDataForTesting() {
            List<User> users = List.of(
                    new User("fáci", "AmyGlassires99", "faci@lmente.hu"),
                    new User("rolcsi", "DamanciaTV7", "rolcsi@cringemail.hu")
                );
            users.forEach(userService()::createPlayerForUser);

            UsableTool tool1 = new UsableTool("dobócsillag", 5, 0, 10, 20);
            NonUsableTool tool2 = new NonUsableTool("kard", 10, 0, 300, 2);
            UsableTool tool3 = new UsableTool("kötszer idk", 0, 20, 20, 20);

            toolService().addOrUpdate(tool1);
            toolService().addOrUpdate(tool2);
            toolService().addOrUpdate(tool3);

            List<Technique> techniques = List.of(
                    new Technique("tűzgolyó", 100, 0, 2000, 40),
                    new Technique("nagy tűzgolyó", 200, 0, 500, 90),
                    new Technique("nagy vízhórukk", 40, 0, 100, 20)
            );
            techniques.forEach(techniqueService()::addOrUpdate);

            List<Action> actions = List.of(
                    new RestAction(8*RestAction.HOURS, 50),
                    new RestAction(4*RestAction.HOURS, 100),
                    new MissionAction(15*Action.SECONDS, 1000, 0),
                    new DuelAction(15*Action.SECONDS, null),
                    new MissionAction(5*RestAction.SECONDS, 4000, 300),
                    new MissionAction(4*RestAction.SECONDS, 10000, 700)
            );
            actions.forEach(actionService()::addOrUpdate);

            Player faci = characterService().getPlayerByName("fáci").get();
            Player rolcsi = characterService().getPlayerByName("rolcsi").get();

            List<NPC> npcs = List.of(
                    new NPC("józsi", 50, 50, 0, 500)
            );
            npcs.forEach(characterService()::addOrUpdate);

            characterService().obtainTool(npcs.get(0), tool1);
    }

    @Bean UserModelAssembler userModelAssembler() { return new UserModelAssembler(); }

    @Bean PlayerModelAssembler characterModelAssembler() { return new PlayerModelAssembler(); }

    @Bean NpcModelAssembler npcModelAssembler() { return new NpcModelAssembler(); }

    @Bean UsableToolModelAssembler usableToolModelAssembler() { return new UsableToolModelAssembler(); }

    @Bean NonUsableToolModelAssembler nonUsableToolModelAssembler() { return new NonUsableToolModelAssembler(); }

    @Bean TechniqueModelAssembler techniqueModelAssembler() { return new TechniqueModelAssembler(); }

    @Bean AdventureActionModelAssembler adventureActionModelAssembler() { return new AdventureActionModelAssembler(); }

    @Bean RestActionModelAssembler actionModelAssembler() { return new RestActionModelAssembler(); }

    @Bean MissionActionModelAssembler missionModelAssembler() { return new MissionActionModelAssembler(); }

    @Bean ScheduledTaskModelAssembler scheduledTaskModelAssembler() { return new ScheduledTaskModelAssembler(); }
}
