package com.railway.booking.filter;

import com.railway.booking.entity.RoleType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private static final String[] PUBLIC_PATH_PAGES = {"login", "logout", "registration"};

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