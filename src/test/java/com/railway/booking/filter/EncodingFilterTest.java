package com.railway.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EncodingFilterTest {

    @Test
    public void testDoFilterCorrecSetCorrectEncoding() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        EncodingFilter filter = new EncodingFilter();
        filter.doFilter(httpServletRequest, httpServletResponse,
                filterChain);
        verify(httpServletRequest).setCharacterEncoding("UTF-8");
    }
}