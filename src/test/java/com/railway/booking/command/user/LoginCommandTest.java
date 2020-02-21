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
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    private static final String EXISTED_EMAIL = "some@gmail.com";
    private static final String WRONG_EMAIL = "wrong@gmail.com";
    private static final String EXISTED_PASSWORD = "qwerty45T";
    private static final User USER = User.builder()
            .withEmail(EXISTED_EMAIL)
            .withPassword(EXISTED_PASSWORD)
            .withRoleType(RoleType.PASSENGER)
            .build();

    private static final User ADMIN = User.builder()
            .withEmail(EXISTED_EMAIL)
            .withPassword(EXISTED_PASSWORD)
            .withRoleType(RoleType.ADMIN)
            .build();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginCommand loginCommand;

    @After
    public void resetMocks() {
        reset(request, response, userService);
    }

    @Test
    public void executeShouldSuccessfullyLoginWithRightCredentials() {
        when(request.getParameter("email")).thenReturn(EXISTED_EMAIL);
        when(request.getParameter("password")).thenReturn(EXISTED_PASSWORD);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);

        when(userService.login(EXISTED_EMAIL, EXISTED_PASSWORD)).thenReturn(USER);

        assertEquals("view/profile.jsp", loginCommand.execute(request, response));
    }

    @Test
    public void executeShouldNotLoginIfEmailIsWrong() {
        when(request.getParameter("email")).thenReturn(WRONG_EMAIL);
        when(request.getParameter("password")).thenReturn(EXISTED_PASSWORD);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);

        when(userService.login(WRONG_EMAIL, EXISTED_PASSWORD)).thenReturn(null);

        assertEquals("view/login.jsp", loginCommand.execute(request, response));
    }

    @Test
    public void executeShouldLoginAdminToAdminPage() {
        when(request.getParameter("email")).thenReturn(WRONG_EMAIL);
        when(request.getParameter("password")).thenReturn(EXISTED_PASSWORD);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        doNothing().when(httpSession).setAttribute(eq("role"), any());

        when(userService.login(WRONG_EMAIL, EXISTED_PASSWORD)).thenReturn(ADMIN);

        assertEquals("view/admin/adminPanel.jsp", loginCommand.execute(request, response));
    }
}