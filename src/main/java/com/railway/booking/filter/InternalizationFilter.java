package com.railway.booking.filter;

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
        if (!isValidLangParam(localeName)) {
            localeName = "en";
        }
        request.getSession().setAttribute("lang", localeName);

        filterChain.doFilter(request, servletResponse);
    }

    private boolean isValidLangParam(String localeName) {
        if (localeName == null) {
            return false;
        }
        if (localeName.equals("en") || localeName.equals("ru") || localeName.equals("ua")) {
            return true;
        }
        return false;
    }
}