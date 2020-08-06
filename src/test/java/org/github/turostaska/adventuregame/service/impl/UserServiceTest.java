package org.github.turostaska.adventuregame.service.impl;

import org.github.turostaska.adventuregame.dao.IUserDao;
import org.github.turostaska.adventuregame.domain.User;
import org.github.turostaska.adventuregame.service.impl.collection.CollectionUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private IUserDao daoMock;

    @InjectMocks
    private CollectionUserService service;

    @BeforeEach
    void setUp() {
        daoMock = mock(IUserDao.class);
        service = new CollectionUserService(daoMock);
    }

    @Test
    public void successfulLogin() {
        String username = "Laci", password = "kutya123";
        User user = new User(username, password, "asd@fgh.hu");

        when(daoMock.getByName(username)).thenReturn(Optional.of(user));

        assertEquals( user, service.tryToLogIn(username, password).get() );
    }

    @Test
    public void unsuccessfulLogin() {
        String username = "Laci", password = "kutya123";
        User user = new User(username, password, "asd@fgh.hu");

        when(daoMock.getByName("Laci")).thenReturn(Optional.of(user));

        assertEquals(Optional.empty(), service.tryToLogIn(username, "cica456"));
    }

    @Test
    public void successfulRegistration() {
        String username = "Laci", password = "KutyaHas452", email = "asd@fgh.hu";

        assertNotEquals( null, service.tryToRegister(username, password, email) );
    }

    @Test
    public void registrationFailsDueToInvalidEmail() {
        String username = "Laci", password = "Kutya123", email = "asd@fghhu";

        assertNull(service.tryToRegister(username, password, email));
    }

    @Test
    public void registrationFailsDueToInvalidPassword() {
        String username = "Laci", password = "Kutya\t123", email = "asd@fgh.hu";

        assertNull(service.tryToRegister(username, password, email));
    }

    @Test
    public void registrationFailsDueToTakenUsername() {
        String username = "Laci", password = "Kutya123", email = "asd@fgh.hu";
        User user = new User(username, password, email);

        when(daoMock.getByName(username)).thenReturn(Optional.of(user));

        assertNull(service.tryToRegister(username, password, email));
    }

    @Test
    public void registrationFailsDueToTakenEmail() {
        String username = "Laci", password = "Kutya123", email = "asd@fgh.hu";
        User user = new User(username, password, email);

        when(daoMock.getByEmail(email)).thenReturn(Optional.of(user));

        assertNull(service.tryToRegister(username, password, email));
    }

    @Test
    public void loginWithNull() {
        String username = null, password = null;
        User user = new User(username, password, "asd@fgh.hu");

        when(daoMock.getByName(username)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), service.tryToLogIn(username, password));
    }

}