package com.railway.booking.filter;

import com.railway.booking.entity.RoleType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {
    private static final String ADMIN_PATH = "/adminPanel";
    private static final String USER_PATH = "/profile";
    private static final String CONTEXT_PATH = "/railway/";
    private static final RoleType ADMIN_ROLE = RoleType.ADMIN;
    private static final RoleType PASSENGER_ROLE = RoleType.PASSENGER;
    private static final String PAGE_403 = "view/access_denied.jsp";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    SecurityFilter securityFilter;

    @After
    public void resetMocks() {
        reset(filterChain);
    }

    @Test
    public void doFilterShouldProhibitAdminResourcesForUser() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(request.getRequestURI()).thenReturn(ADMIN_PATH);
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(httpSession.getAttribute("role")).thenReturn(PASSENGER_ROLE);

        securityFilter.doFilter(request, response, filterChain);

        verify(requestDispatcher).forward(request, response);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void doFilterShouldAllowUserAccessToGeneralResources() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(request.getRequestURI()).thenReturn(USER_PATH);
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
        when(httpSession.getAttribute("role")).thenReturn(PASSENGER_ROLE);

        securityFilter.doFilter(request, response, filterChain);
        securityFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(2)).doFilter(request, response);
    }
}