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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.frontend.component.LoggedInStatusBar;
import org.github.turostaska.adventuregame.frontend.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("login")
@SpringUI
@SpringViewDisplay
@Slf4j
public class MainUI extends UI implements ViewDisplay {
    private Panel springViewDisplay;
    final private CssLayout navigationBar = new CssLayout();
    private final LoggedInStatusBar statusBar = new LoggedInStatusBar();

    @Getter
    private User loggedInUser;

    @Autowired Navigator navigator;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        statusBar.invalidate(loggedInUser);

        if (loggedInUser != null) {
            loginButton.setVisible(false);
            registerButton.setVisible(false);
            logoutButton.setVisible(true);
        } else {
            loginButton.setVisible(true);
            registerButton.setVisible(true);
            logoutButton.setVisible(false);
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        final HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        setContent(root);
        root.setStyleName("ui-layout-root");

        setNavigator(navigator);

        getPage().setTitle("nevelj narutót but not in a bad way");

        initNavigationBar(root);

        springViewDisplay = new Panel();
        springViewDisplay.setStyleName("spring-view-display");
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 0.8f);
        root.setExpandRatio(navigationBar, 0.2f);
    }

    private Button loginButton;
    private Button registerButton;
    private Button logoutButton;

    private void initNavigationBar(HorizontalLayout root) {
        loginButton = createNavigationButton("Login", LoginView.NAME);
        registerButton = createNavigationButton("Register", RegistrationView.NAME);
        logoutButton = createNavigationButton("Logout", LoginView.NAME);

        logoutButton.addClickListener(event -> setLoggedInUser(null));

        navigationBar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

        navigationBar.addComponents(statusBar);

        navigationBar.addComponent(loginButton);
        navigationBar.addComponent(registerButton);
        navigationBar.addComponent(createNavigationButton("Item Shop", ItemShopView.NAME));
        navigationBar.addComponent(createNavigationButton("Scroll Shop", ScrollShopView.NAME));
        navigationBar.addComponent(createNavigationButton("Missions", MissionActionView.NAME));
        navigationBar.addComponent(createNavigationButton("Rest", RestActionView.NAME));
        navigationBar.addComponent(createNavigationButton("Adventures", AdventureActionView.NAME));
        navigationBar.addComponent(createNavigationButton("Character list", CharacterGridView.NAME));
        navigationBar.addComponent(logoutButton);

        setLoggedInUser(null);

        root.addComponent(navigationBar);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        button.setHeight("60px");
        return button;
    }
}
