package com.railway.booking.command.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationFormCommandTest {
    private static final String EXPECTED_VIEW = "/view/registration.jsp";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private RegistrationFormCommand registrationFormCommand;

    @After
    public void resetMocks() {
        reset(request, response);
    }

    @Test
    public void executeShouldReturnCorrectView() {
        String actualView = registrationFormCommand.execute(request, response);

        assertEquals(EXPECTED_VIEW, actualView);
    }
}