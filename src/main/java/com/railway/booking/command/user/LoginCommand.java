package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = getLoggedUser(email, password);
        if (user != null) {
            setUserAndRoleToSession(request, user.getRoleType(), email);
            return "trains.jsp";
        } else {
            return "login.jsp";
        }
    }

    private void setUserAndRoleToSession(HttpServletRequest request,
                                         RoleType role, String email) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
    }

    private User getLoggedUser(String email, String password) {
        return userService.login(email, password);
    }
}