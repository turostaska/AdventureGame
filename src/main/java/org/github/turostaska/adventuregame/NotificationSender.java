package org.github.turostaska.adventuregame;

import org.github.turostaska.adventuregame.domain.Action;
import org.github.turostaska.adventuregame.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public final class NotificationSender {
    private NotificationSender() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("c83a48391a9a93");
        mailSender.setPassword("6ff37b1c63f493");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    private static NotificationSender instance;
    private JavaMailSenderImpl mailSender;

    public static NotificationSender getInstance() {
        if (instance == null)
            instance = new NotificationSender();

        return instance;
    }

    private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);

    public void sendActionFinishedNotification(User receiver, Action executedAction) {
        var message = new SimpleMailMessage();
        message.setFrom("adventure_game@cringo.mail");
        message.setTo(receiver.getEmail());
        message.setSubject("Action finished");
        message.setText(String.format("You have finished the following task: %s\nVisit the site for more information!",
                executedAction.description()));
        mailSender.send(message);
    }
}
