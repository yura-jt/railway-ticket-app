package com.railway.booking.command.user;

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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {
    private static final String EXPECTED_VIEW = "view/login.jsp";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession httpSession;

    @Mock
    private UserService userService;

    @InjectMocks
    private LogoutCommand logoutCommand;

    @After
    public void resetMocks() {
        reset(request, response, userService, httpSession);
    }

    @Test
    public void executeShouldReturnCorrectView() {
        when(request.getSession()).thenReturn(httpSession);
        LogoutCommand logoutCommand = new LogoutCommand();
        String actualView = logoutCommand.execute(request, response);

        assertEquals(EXPECTED_VIEW, actualView);
    }

    @Test
    public void executeShouldInvalidateUserData() {
        when(request.getSession()).thenReturn(httpSession);

        logoutCommand.execute(request, response);

        verify(request, times(1)).getSession();
        verify(httpSession, times(1)).removeAttribute("userFirstName");
        verify(httpSession, times(1)).removeAttribute("role");
        verify(httpSession, times(1)).invalidate();
    }
}