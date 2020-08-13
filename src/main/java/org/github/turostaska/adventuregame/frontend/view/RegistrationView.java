package org.github.turostaska.adventuregame.frontend.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.frontend.component.RegistrationForm;
import org.github.turostaska.adventuregame.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = RegistrationView.NAME)
@Slf4j
public class RegistrationView extends GridLayout implements View {
    public static final String NAME = "registration";

    private RegistrationForm registrationForm = new RegistrationForm();

    @Autowired
    private IUserService userService;

    public RegistrationView() {
        super(1, 2);
    }

    private void addRegistrationView() {
        registrationForm.setWidthUndefined();

        Panel registrationFormPanel = new Panel(registrationForm);
        registrationFormPanel.setStyleName("registration-form-panel");
        registrationFormPanel.setWidthUndefined();

        addComponent(registrationFormPanel, 0, 0);
        setComponentAlignment(registrationFormPanel, Alignment.BOTTOM_CENTER);
    }

    @PostConstruct
    public void init() {
        setSizeFull();

        addRegistrationView();
        // ez így geci ronda but so is vaadin
        registrationForm.registerClickListener(userService);
    }
}



