package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            return "view/login.jsp";
        }
        User user = getLoggedUser(email, password);
        if (user == null) {
            return "view/login.jsp";
        }

        setUserAndRoleToSession(request, user.getRoleType(), email);
        if (user.getRoleType() == RoleType.ADMIN) {
            return "view/admin.jsp";
        } else {
            return "view/access_denied.jsp";
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