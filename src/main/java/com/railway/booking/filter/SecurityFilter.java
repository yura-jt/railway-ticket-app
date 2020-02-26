package com.railway.booking.filter;

import com.railway.booking.entity.RoleType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * (@link SecurityFilter) is the filter that responsible for managing access to resource dependant on the roles
 */
public class SecurityFilter implements Filter {
    /**
     * Provides resources with Permit-all access level for all users.
     * This is used by an application while giving access to resources.
     */
    private static final String[] PUBLIC_PATH_PAGES = {"login", "logout", "registration"};

    /**
     * Provides list of user defined security actions and rules, needed for implementing
     * security configuration of application.
     * Security configuration is role dependent authorization and authenticate system.
     *
     * @param servletRequest  received servlet request, that contains information from client
     * @param servletResponse response servlet, sending to user
     * @param filterChain     object used for invocation filter chaining
     * @throws IOException      if the value of any field is out of range,
     *                          or if the day-of-month is invalid for the month-year
     * @throws ServletException if an exception has occurred that interferes with
     *                          the filter's normal operation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String path = request.getRequestURI();
        final RoleType role = (RoleType) request.getSession().getAttribute("role");
        final boolean isAdminPath = path.toLowerCase().contains("admin");
        final boolean isPublicPath = checkIfPathIsPublic(path, request.getContextPath());

        if (isAdminPath && role != RoleType.ADMIN ||
                (!isAdminPath && role == RoleType.ADMIN && !isPublicPath) ||
                !isPublicPath && role == null) {
            request.getRequestDispatcher("view/access_denied").forward(request, servletResponse);
            return;
        }

        filterChain.doFilter(request, servletResponse);
    }

    private boolean checkIfPathIsPublic(String path, String contextPath) {
        if (contextPath.equalsIgnoreCase(path) || (contextPath + "/").equalsIgnoreCase(path)) {
            return true;
        }
        for (String pageName : PUBLIC_PATH_PAGES) {
            if (path.toLowerCase().contains(pageName)) {
                return true;
            }
        }
        return false;
    }
}