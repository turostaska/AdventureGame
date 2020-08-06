package org.github.turostaska.adventuregame;

import org.github.turostaska.adventuregame.domain.Action;
import org.github.turostaska.adventuregame.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//TO DO: +1, nagyából jól nézki, de esetleg érdemes megnézni a Spring Mail-t.
public final class NotificationSender {
    private NotificationSender() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.smtp.host");
        mailSender.setPort(25);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
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
                executedAction.toString()));
        mailSender.send(message);
    }
}
