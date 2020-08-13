package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.IUserService;

@Slf4j
public class RegistrationForm extends FormLayout {
    private TextField usernameField = new TextField("username");
    private TextField emailField = new TextField("email address");
    private TextField passwordField = new TextField("password");

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
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }));
    }
}
