package com.railway.booking.command.user;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.service.UserService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    private static final String EXPECTED_SUCCESSFUL_REGISTER_VIEW = "/view/profile.jsp";
    private static final String EXPECTED_FAIL_REGISTER_VIEW = "/view/registration.jsp";
    private static final String EXPECTED_MISMATCH_REGISTER_VIEW = "registrationForm";
    private static final String FIRST_NAME = "Isaac";
    private static final String LAST_NAME = "Asimov";
    private static final String PASSWORD = "encoded_password";
    private static final String PHONE_NUMBER = "+47213245654";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final String WRONG_PASSWORD_REPEAT = "wrong password";
    private static final RoleType ROLE_TYPE = RoleType.PASSENGER;
    private static final User USER = initUser();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationCommand registrationCommand;

    @After
    public void resetMocks() {
        reset(request, response, userService);
    }

    @Test
    public void executeShouldReturnRegistrationFormIfPasswordsMismatches() {
        when(request.getParameter("first_name")).thenReturn(FIRST_NAME);
        when(request.getParameter("last_name")).thenReturn(LAST_NAME);
        when(request.getParameter("email")).thenReturn(USER_EMAIL);
        when(request.getParameter("phone_number")).thenReturn(PHONE_NUMBER);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("password_repeated")).thenReturn(WRONG_PASSWORD_REPEAT);

        when(userService.register(any())).thenReturn(false);

        String actual = registrationCommand.execute(request, response);
        assertEquals(EXPECTED_MISMATCH_REGISTER_VIEW, actual);
        verifyZeroInteractions(userService);
    }

    @Test
    public void executeShouldReturnRegistrationFormIfUserExist() {
        when(request.getParameter("first_name")).thenReturn(FIRST_NAME);
        when(request.getParameter("last_name")).thenReturn(LAST_NAME);
        when(request.getParameter("email")).thenReturn(USER_EMAIL);
        when(request.getParameter("phone_number")).thenReturn(PHONE_NUMBER);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("password_repeated")).thenReturn(PASSWORD);

        when(userService.register(any())).thenReturn(false);

        String actual = registrationCommand.execute(request, response);
        assertEquals(EXPECTED_FAIL_REGISTER_VIEW, actual);
        verify(userService, times(1)).register(USER);
    }

    @Test
    public void executeShouldReturnProfilePageIfUserRegistered() {
        when(request.getParameter("first_name")).thenReturn(FIRST_NAME);
        when(request.getParameter("last_name")).thenReturn(LAST_NAME);
        when(request.getParameter("email")).thenReturn(USER_EMAIL);
        when(request.getParameter("phone_number")).thenReturn(PHONE_NUMBER);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("password_repeated")).thenReturn(PASSWORD);

        when(userService.register(any())).thenReturn(true);

        String actual = registrationCommand.execute(request, response);
        assertEquals(EXPECTED_SUCCESSFUL_REGISTER_VIEW, actual);
        verify(userService, times(1)).register(USER);
    }

    private static User initUser() {
        return User.builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(USER_EMAIL)
                .withPhoneNumber(PHONE_NUMBER)
                .withPassword(PASSWORD)
                .withRoleType(ROLE_TYPE)
                .build();
    }
}