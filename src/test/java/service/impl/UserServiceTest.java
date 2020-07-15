package service.impl;

import dao.IUserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private IUserDao dao;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(dao);
    }
}