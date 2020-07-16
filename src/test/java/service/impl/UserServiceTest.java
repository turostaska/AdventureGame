package service.impl;

import dao.IUserDao;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.IUserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private IUserDao daoMock;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        daoMock = mock(IUserDao.class);
        service = new UserService(daoMock);
    }

    @Test
    public void successfulLogin() {
        String username = "Laci", password = "kutya123";
        User user = new User(username, password, "asd@fgh.hu");

        when(daoMock.getByName("Laci")).thenReturn(Optional.of(user));

        assertTrue( service.tryToLogIn(username, password) );
    }

    @Test
    public void unsuccessfulLogin() {
        String username = "Laci", password = "kutya123";
        User user = new User(username, password, "asd@fgh.hu");

        when(daoMock.getByName("Laci")).thenReturn(Optional.of(user));

        assertFalse( service.tryToLogIn(username, "cica456") );
    }

    @Test
    public void successfulRegistration() {
        String username = "Laci", password = "kutya123", email = "asd@fgh.hu";


        assertEquals( IUserService.RegistrationResult.SUCCESS, service.tryToRegister(username, password, email) );
    }

    public void registrationFailsDueToInvalidEmail() {
        String username = "Laci", password = "kutya123", email = "asd@fghhu";

        assertEquals( IUserService.RegistrationResult.INVALID_EMAIL, service.tryToRegister(username, password, email) );
    }

}