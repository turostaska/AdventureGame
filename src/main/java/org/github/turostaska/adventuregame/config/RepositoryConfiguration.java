package org.github.turostaska.adventuregame.config;

import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.*;
import org.github.turostaska.adventuregame.service.*;
import org.github.turostaska.adventuregame.service.impl.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Slf4j
@Profile("repository")
public class RepositoryConfiguration {
    @Bean
    public IScheduledTaskService scheduledTaskService() {
        return new RepositoryScheduledTaskService();
    }

    @Bean
    public ICharacterService characterService() {
        return new RepositoryCharacterService();
    }

    @Bean
    public IToolService toolService() { return new RepositoryToolService(); }

    @Bean
    public IUserService userService() { return new RepositoryUserService(); }

    @Bean
    public ITechniqueService techniqueService() {
        return new RepositoryTechniqueService();
    }

    @Bean
    public IActionService actionService() {
        return new RepositoryActionService();
    }

    @Bean
    CommandLineRunner initUserDatabase() {
        return args -> {
            log.info("Preloading...");
            loadInitialData();
            log.info("Preloading finished.");

            log.info("Rescheduling unfinished tasks...");
            scheduledTaskService().rescheduleUnfinishedTasksUponInitialization();
            log.info("Rescheduling finished.");
        };
    }

    private void loadInitialData() {
        loadInitialTools();
        loadInitialTechniques();
        loadInitialActions();
        loadNPCs();
    }

    private void loadNPCs() {
        if (!characterService().npcsArePresent()) {
            NPC stevens = new NPC("Saleh Stevens", 150, 80, 500, 10000);
            NPC makos = new NPC("Pickle Mákos", 100, 120, 200, 6000);
            NPC kevinson = new NPC("Boolean Kevinson", 50, 50, 0, 1000);

            characterService().learnTechnique(stevens, techniqueService().getByName("Legit MMA").orElseThrow());
            characterService().obtainTool(makos, toolService().getNonUsableToolByName("Katana").orElseThrow());

            List<NPC> npcs = List.of(
                    stevens, kevinson, makos
            );
            npcs.forEach(characterService()::addOrUpdate);
        }
    }

    private void loadInitialActions() {
        if (actionService().isEmpty()) {
            AdventureAction action = new AdventureAction(4 * Action.HOURS,
                    List.of(
                            techniqueService().getByName("Tell a magon jokette").orElseThrow(),
                            techniqueService().getByName("Play >imagine dragons").orElseThrow()),
                    List.of(
                            toolService().getUsableToolByName("Deserion keksz").orElseThrow()
                    ), 400);
            actionService().addOrUpdate(action);

            List<Action> actions = List.of(
                    new RestAction(24 * Action.HOURS, 0),
                    new RestAction(12 * Action.HOURS, 400),
                    new RestAction(8  * Action.HOURS, 600),
                    new RestAction(6  * Action.HOURS, 1000),
                    new RestAction(4  * Action.HOURS, 1600),

                    new DuelAction(10 * Action.MINUTES, null),

                    new MissionAction(Action.HOURS, 500,  0),
                    new MissionAction(Action.HOURS, 1000, 80),
                    new MissionAction(Action.HOURS, 1500, 250),
                    new MissionAction(Action.HOURS, 2000, 500),
                    new MissionAction(Action.HOURS, 3000, 700),
                    new MissionAction(Action.HOURS, 4000, 1000)
            );
            actions.forEach(actionService()::addOrUpdate);
        }
    }

    private void loadInitialTechniques() {
        if (techniqueService().isEmpty()) {
            List<Technique> techniques = List.of(
                    new Technique("Fireball", 15, 0, 1200, 20),
                    new Technique("Throw rocks", 10, 0, 800, 0),
                    new Technique("Legit MMA", 25, 0, 5000, 0),
                    new Technique("A walk at the Guami piac", 60, 0, 7000, 60),
                    new Technique("Slightly bigger fireball", 20, 0, 1800, 25),
                    new Technique("Not quite as big of a fireball", 30, 0, 2400, 35),
                    new Technique("Nagy vízhórukk", 25, 0, 2000, 30),
                    new Technique("Healing technique", 0, 25, 1800, 25),
                    new Technique("Play >imagine dragons", 100, -70, 6000, 90),
                    new Technique("Tell a magon jokette", 100, 0, 12000, 100),
                    new Technique("Have them use Vaadin no jutsu", 100, 0, 12000, 100),
                    new Technique("Big fireball", 40, 0, 3600, 50)
            );
            techniques.forEach(techniqueService()::addOrUpdate);
        }
    }

    private void loadInitialTools() {
        if (toolService().isEmpty()) {
            List<Tool> tools = List.of(
                    new UsableTool("Shuriken", 5, 0, 200, 20),
                    new UsableTool("Deserion keksz", 0, 40, 1000, 20),
                    new UsableTool("Bomb", 16, 0, 800, 10),
                    new NonUsableTool("Tapétavágó", 5, 0, 500, 2),
                    new NonUsableTool("Rozsdás szög", 5, 0, 700, 4),
                    new NonUsableTool("Kihegyezett fogkefe", 10, 0, 1200, 2),
                    new NonUsableTool("Axe", 30, 0, 3200, 1),
                    new NonUsableTool("Katana", 20, 0, 2500, 2)
            );
            for (Tool tool : tools) {
                if (tool instanceof UsableTool)
                    toolService().addOrUpdate(((UsableTool) tool));
                else
                    toolService().addOrUpdate(((NonUsableTool) tool));
            }
        }
    }

}
