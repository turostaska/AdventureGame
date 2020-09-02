package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.frontend.component.CustomLoginForm;
import org.github.turostaska.adventuregame.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = LoginView.NAME)
@Slf4j
public class LoginView extends GridLayout implements View {
    public static final String NAME = "";

    private final CustomLoginForm customLoginForm = new CustomLoginForm();
    private final Label registrationLabel = new Label("Nincs profilod? " +
            "<a href='/#!" + RegistrationView.NAME + "'>Mit csinálsz az életeddel?</a>", ContentMode.HTML);

    @Autowired
    private IUserService userService;

    public LoginView() {
        super(1, 2);
    }

    @PostConstruct
    protected void init() {
        setSizeFull();

        addLoginPanel();
        addRegistrationLabel();

        customLoginForm.registerClickListener(userService);
    }

    private void addRegistrationLabel() {
        addComponent(registrationLabel, 0, 1);
        setComponentAlignment(registrationLabel, Alignment.TOP_CENTER);
    }

    Panel loginPanel = new Panel(customLoginForm);

    private void addLoginPanel() {
        loginPanel.setWidthUndefined();

        addComponent(loginPanel, 0, 0);
        setComponentAlignment(loginPanel, Alignment.BOTTOM_CENTER);
    }

}
