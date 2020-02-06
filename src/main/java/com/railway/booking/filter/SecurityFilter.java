package com.railway.booking.filter;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String path = request.getRequestURI();
        final User user = (User) request.getSession().getAttribute("User.Role");
        final RoleType roleType = user.getRoleType();

        if (path.contains("manage") && roleType != RoleType.ADMIN) {
            request.getRequestDispatcher("view/access_denied.jps").forward(request, servletResponse);
        }
        filterChain.doFilter(request, servletResponse);
    }
}
