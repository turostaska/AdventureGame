package org.github.turostaska;
//TODO: esetleg egy alkalmazás specifikus package-et még érdemes lenne berakni az azonosítód után.

//TODO: A nem használt package-et töröljük. Esetleg intellij-be be lehet konfigurálni hogy auto törölje azokat.
import org.github.turostaska.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
