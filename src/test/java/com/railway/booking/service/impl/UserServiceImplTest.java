package com.railway.booking.service.impl;

import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.service.Paginator;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.validator.UserValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final Integer USER_ID = 1;
    private static final String FIRST_NAME = "Isaac";
    private static final String LAST_NAME = "Asimov";
    private static final String PASSWORD = "encoded_password";
    private static final String PHONE_NUMBER = "+47213245654";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final RoleType ROLE_TYPE = RoleType.PASSENGER;
    private static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    private static final String ENCODE_INCORRECT_PASSWORD = "encode_incorrect_password";

    private static final User USER = initUser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @Mock
    private UserValidator userValidator;

    @Mock
    private Paginator paginator;

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
        when(userValidator.isValid(any(User.class))).thenReturn(true);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.register(USER);

        verify(userValidator).isValid(any(User.class));
        verify(userDao).findByEmail(anyString());
        verify(userDao).save(any(User.class));
    }

    @Test
    public void userShouldNotRegisterWithInvalidPasswordOrEmail() {
        when(userValidator.isValid(any(User.class))).thenReturn(false);

        userService.register(USER);
        verify(userValidator).isValid(any(User.class));
    }

    @Test
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
        when(userValidator.isValid(any(User.class))).thenReturn(false);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(USER));
        when(userDao.save(any(User.class))).thenReturn(false);

        userService.register(USER);
        verify(userValidator).isValid(any(User.class));
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

        final User actual = userDao.findByEmail(USER_EMAIL).orElse(null);
        assertEquals(USER, actual);
        verify(userDao).findByEmail(USER_EMAIL);
    }

    @Test
    public void findByEmailShouldReturnNull() {
        userService.register(USER);
        when(userDao.findByEmail("1@mail")).thenReturn(Optional.empty());

        final User actual = userDao.findByEmail("1@mail").orElse(null);
        assertNull(actual);
        verify(userDao).findByEmail("1@mail");
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(paginator.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(userDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<User> actual = userService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }

    private static User initUser() {
        return User.builder()
                .withId(USER_ID)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(USER_EMAIL)
                .withPhoneNumber(PHONE_NUMBER)
                .withPassword(PASSWORD)
                .withRoleType(ROLE_TYPE)
                .build();
    }
}