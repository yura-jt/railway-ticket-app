package com.railway.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CrossScriptingFilterTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;

    @Test
    public void testDoFilterCallsToMethodCleanerAndChangeParams() throws ServletException, IOException {
        HttpSession httpSession = mock(HttpSession.class);
//       when(requestWrapper.getHeader("<b onmouseover=alert(‘XSS testing!‘)></b>")).thenReturn(any());

        CrossScriptingFilter filter = new CrossScriptingFilter();
        filter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
    }
}