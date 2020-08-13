package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.navigator.View;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.IUserService;

@Slf4j
public class CustomLoginForm extends LoginForm implements View {
    public void registerClickListener(IUserService userService) {
        addLoginListener((LoginForm.LoginListener)event -> {
            try {
                User user = userService.tryToLogIn(event.getLoginParameter("username"),
                        event.getLoginParameter("password"));
                //addComponent(new Label(user.getUserName() + " has signed in."));
                log.info(user.getUserName() + " has signed in.");
            } catch (Exception e) {
                //addComponent(new Label(e.getMessage()));
                log.info(e.getMessage());
            }
        });
    }

    @Override
    protected PasswordField createPasswordField() {
        PasswordField field = new PasswordField();
        field.setCaption(null);
        field.setPlaceholder("password");
        return field;
    }

    @Override
    protected TextField createUsernameField() {
        TextField field = new TextField();
        field.setCaption(null);
        field.setPlaceholder("username");
        return field;
    }

}
