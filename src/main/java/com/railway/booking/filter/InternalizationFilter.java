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
        String requestParam = servletRequest.getParameter("lang");
        String sessionParam = (String) request.getSession().getAttribute("lang");

        String localeName = getLocalName(requestParam, sessionParam);

        request.getSession().setAttribute("lang", localeName);

        filterChain.doFilter(request, servletResponse);
    }

    private String getLocalName(String requestParam, String sessionParam) {
        boolean isSessionParamEmpty = sessionParam == null;

        if (isSessionParamEmpty && !isValidLangParam(requestParam)) {
            return "en";
        } else if (!isSessionParamEmpty && !isValidLangParam(requestParam)) {
            return sessionParam;
        } else {
            return requestParam;
        }
    }

    private boolean isValidLangParam(String localeName) {
        if (localeName == null) {
            return false;
        }
        return localeName.equals("en") || localeName.equals("ru") || localeName.equals("ua");
    }
}