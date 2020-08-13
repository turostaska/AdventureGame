package org.github.turostaska.adventuregame.frontend.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.frontend.view.LoginView;
import org.github.turostaska.adventuregame.frontend.view.RegistrationView;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("login")
@SpringUI
@SpringViewDisplay
@Slf4j
public class MainUI extends UI implements ViewDisplay {
    private Panel springViewDisplay;

    @Autowired Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        final HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        setContent(root);
        root.setStyleName("ui-layout-root");

        setNavigator(navigator);

        getPage().setTitle("nevelj narutót but not in a bad way");

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        navigationBar.addComponent(createNavigationButton("Login",
                LoginView.NAME));
        navigationBar.addComponent(createNavigationButton("Register",
                RegistrationView.NAME));
        root.addComponent(navigationBar);

        springViewDisplay = new Panel();
        springViewDisplay.setStyleName("spring-view-display");
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this
        // to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        button.setHeight("60px");
        return button;
    }
}
