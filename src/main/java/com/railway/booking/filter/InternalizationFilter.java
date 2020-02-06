package com.railway.booking.filter;

import com.railway.booking.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class InternalizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        String localeName = servletRequest.getParameter("lang");
        if (localeName != null) {
            request.getSession().setAttribute("lang", localeName);
        }

        filterChain.doFilter(request, servletResponse);
    }
}