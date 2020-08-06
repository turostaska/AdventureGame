package org.github.turostaska.adventuregame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        var application = new SpringApplication(Application.class);
        application.setAdditionalProfiles("repository");
        application.run(args);
    }
}
