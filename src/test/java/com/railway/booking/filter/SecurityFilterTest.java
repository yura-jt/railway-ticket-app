package com.railway.booking.filter;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {
    private static final String ADMIN_PATH = "/admin/manage";
    private static final String USER_PATH = "/user/trains";
    private static final User USER = User.builder()
            .withRoleType(RoleType.PASSENGER)
            .build();
    private static final String PAGE_403 = "view/access_denied.jsp";

    @Mock
    private FilterChain filterChain;

    @After
    public void resetMocks() {
        reset(filterChain);
    }

    @Test
    public void doFilterShouldProhibitAdminResourcesForUser() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getRequestURI()).thenReturn(ADMIN_PATH);
        when(httpSession.getAttribute("user")).thenReturn(USER);
        when(request.getRequestDispatcher(PAGE_403)).thenReturn(requestDispatcher);

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.doFilter(request, response, filterChain);


        securityFilter.doFilter(request, response, filterChain);

        verifyZeroInteractions(filterChain);
    }

    @Test
    public void doFilterShouldAllowUserToGeneralResources() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession httpSession = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getRequestURI()).thenReturn(USER_PATH);
        when(httpSession.getAttribute("user")).thenReturn(USER);

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.doFilter(request, response, filterChain);


        securityFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(2)).doFilter(request, response);
    }
}