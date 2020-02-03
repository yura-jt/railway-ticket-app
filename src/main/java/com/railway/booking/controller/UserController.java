package com.railway.booking.controller;

import com.railway.booking.context.ApplicationInjector;
import com.railway.booking.entity.User;
import com.railway.booking.entity.enums.RoleType;
import com.railway.booking.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserController extends HttpServlet {
    private final UserService userService;

    public UserController() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.userService = injector.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = (User) req.getSession().getAttribute("user");
        if (user.getRoleType() != RoleType.ADMIN) {
            req.getRequestDispatcher("view/not_admin.jsp").forward(req, resp);
        }
        final List<User> users = userService.findAll(1);
        req.setAttribute("users", users);

        req.getRequestDispatcher("view/users.jsp").forward(req, resp);
    }
}
