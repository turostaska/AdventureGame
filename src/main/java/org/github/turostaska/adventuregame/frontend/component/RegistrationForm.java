package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.IUserService;

@Slf4j
public class RegistrationForm extends FormLayout {
    private TextField usernameField = new TextField("username");
    private TextField emailField = new TextField("email address");
    private PasswordField passwordField = new PasswordField("password");

    private Button registrationButton = new Button("register");

    public RegistrationForm() {

        addComponents(usernameField,
                emailField,
                passwordField,
                registrationButton);
    }

    public void registerClickListener(IUserService userService) {
        registrationButton.addClickListener((event -> {
            try {
                User user = userService.tryToRegister(usernameField.getValue(), passwordField.getValue(), emailField.getValue());
                log.info(user.getUserName() + " has registered a new profile.");
                Notification.show("Successful registration.", Notification.Type.HUMANIZED_MESSAGE);
            } catch (Exception e) {
                log.info(e.getMessage());
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        }));
    }
}
