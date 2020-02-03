package com.railway.booking.service.impl;

import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.User;
import com.railway.booking.entity.enums.RoleType;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.exception.EntityAlreadyExistException;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final Integer USER_ID = 1;
    private static final String FIRST_NAME = "Isaac";
    private static final String LAST_NAME = "Asimov";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "+47213245654";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final RoleType ROLE_TYPE = RoleType.PASSENGER;
    private static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    private static final String ENCODE_INCORRECT_PASSWORD = "encode_incorrect_password";

    private static final User USER = User.builder()
            .withId(USER_ID)
            .withFirstName(FIRST_NAME)
            .withLastName(LAST_NAME)
            .withEmail(USER_EMAIL)
            .withPhoneNumber(PHONE_NUMBER)
            .withPassword(PASSWORD)
            .withRoleType(ROLE_TYPE)
            .build();

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncryptor passwordEncryptor;
    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userDao, passwordEncryptor, userValidator);
    }

    @Test
    public void userShouldLoginSuccessfully() {
        when(passwordEncryptor.encrypt(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(USER));

        final User user = userService.login(USER_EMAIL, PASSWORD);

        assertNotNull(user);
        verify(passwordEncryptor).encrypt(eq(PASSWORD));
        verify(userDao).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldNotLoginAsThereIsNotUserWithSuchEmail() {
        when(passwordEncryptor.encrypt(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());

        final User user = userService.login(USER_EMAIL, PASSWORD);

        assertNull(user);
        verify(passwordEncryptor).encrypt(eq(PASSWORD));
        verify(userDao).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldNotLoginAsPasswordIsIncorrect() {
        when(passwordEncryptor.encrypt(eq(INCORRECT_PASSWORD))).thenReturn(ENCODE_INCORRECT_PASSWORD);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(USER));

        final User user = userService.login(USER_EMAIL, INCORRECT_PASSWORD);

        assertNull(user);
        verify(passwordEncryptor).encrypt(eq("INCORRECT_PASSWORD"));
        verify(userDao).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldRegisterSuccessfully() {
        doNothing().when(userValidator).validate(any(User.class));
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.register(USER);

        verify(userValidator).validate(any(User.class));
        verify(userDao).findByEmail(anyString());
        verify(userDao).save(any(User.class));
    }

    @Test(expected = ValidateException.class)
    public void userShouldNotRegisterWithInvalidPasswordOrEmail() {
        doThrow(ValidateException.class).when(userValidator).validate(any(User.class));

        userService.register(USER);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
        doNothing().when(userValidator).validate(any(User.class));
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(USER));
        doNothing().when(userDao).save(any(User.class));

        userService.register(USER);
    }

    @Test
    public void findByIdShouldReturnSavedUser() {
        userService.register(USER);
        when(userDao.findById(USER_ID)).thenReturn(Optional.of(USER));

        final User actual = userService.findById(USER_ID);
        assertEquals(USER, actual);
        verify(userDao).findById(USER_ID);
    }

    @Test
    public void findByIdShouldReturnNull() {
        userService.register(USER);
        when(userDao.findById(USER_ID + 1)).thenReturn(Optional.empty());

        final User actual = userService.findById(USER_ID + 1);
        assertNull(actual);
        verify(userDao).findById(USER_ID + 1);
    }

    @Test
    public void findByEmailShouldReturnSavedUser() {
        when(userDao.findByEmail(USER_EMAIL)).thenReturn(Optional.of(USER));

        final User actual = userService.findByEmail(USER_EMAIL);
        assertEquals(USER, actual);
        verify(userDao).findByEmail(USER_EMAIL);
    }

    @Test
    public void findByEmailShouldReturnNull() {
        userService.register(USER);
        when(userDao.findByEmail("1@mail")).thenReturn(Optional.empty());

        final User actual = userService.findByEmail("1@mail");
        assertNull(actual);
        verify(userDao).findByEmail("1@mail");
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(userDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<User> actual = userService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }
}