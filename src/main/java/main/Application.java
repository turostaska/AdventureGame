package main;

import config.CollectionConfiguration;
import domain.Player;
import domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.impl.CharacterService;

//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(CollectionConfiguration.class);
        ctx.refresh();

        CharacterService service = ctx.getBean(CharacterService.class);
        User user = new User("fáci", "SzakgyakChan3000", "faci@lmente.hu");
        service.addOrUpdate(new Player(user));

        //System.out.println(service.getPlayerByName("fáci").get().getName());

        //SpringApplication.run(Application.class, args);

        
    }
}
