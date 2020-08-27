package org.github.turostaska.adventuregame.config;

import org.github.turostaska.adventuregame.controller.assembler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
