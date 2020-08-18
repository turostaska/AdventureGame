package org.github.turostaska.adventuregame.frontend.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.frontend.view.CharacterSheetView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LoggedInStatusBar extends HorizontalLayout {
    Panel loggedInPanel = new Panel("No user logged in");
    Image profilePicture = new Image(null, new ThemeResource("./profile-default.png"));

    public LoggedInStatusBar() {
        profilePicture.setWidth("100px");
        profilePicture.setHeight("100px");

        loggedInPanel.setCaptionAsHtml(true);

        addComponents(profilePicture, loggedInPanel);

        setExpandRatio(profilePicture, 3);
        setExpandRatio(loggedInPanel, 7);

        setComponentAlignment(loggedInPanel, Alignment.MIDDLE_CENTER);
    }

    public void invalidate(User newUser) {
        loggedInPanel.setCaption("What's cooking " + newUser.getUserName() + " :^)<br>" +
                "Logged in since " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        setClickListenerForPicture(newUser);
    }

    private Registration profilePicRegistration;

    private void setClickListenerForPicture(User newUser) {
        if (profilePicRegistration != null)
            profilePicRegistration.remove();

        if (newUser != null) {
            profilePicRegistration = profilePicture.addClickListener(event ->
                    getUI().getNavigator().navigateTo(CharacterSheetView.NAME + "/" + newUser.getPlayer().getId()));
        }
    }
}
