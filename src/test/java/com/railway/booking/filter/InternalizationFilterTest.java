package com.railway.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InternalizationFilterTest {
    private static final String DEFAULT_LOCALE = "en";
    private static final String LOCAL_PARAM_NAME = "lang";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;


    @Test
    public void doFilterShouldSetDefaultLocale() throws Exception {
        when(request.getSession()).thenReturn(session);

        InternalizationFilter localeFilter = new InternalizationFilter();
        localeFilter.doFilter(request, response, chain);

        verify(session).setAttribute(LOCAL_PARAM_NAME, DEFAULT_LOCALE);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void doFilterShouldNotChangeLocaleForValidLocale() throws Exception {
        when(request.getSession()).thenReturn(session);
        session.setAttribute(LOCAL_PARAM_NAME, "ua");

        InternalizationFilter localeFilter = new InternalizationFilter();
        localeFilter.doFilter(request, response, chain);

        verify(session, times(1)).setAttribute(anyString(), eq("ua"));
        verify(chain).doFilter(request, response);
    }
}