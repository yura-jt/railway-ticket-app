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

        User user = getLoggedUser(email, password);

        if (user == null) {
            return "view/login.jsp";
        }

        setUserAndRoleToSession(request, user, email);

        if (user.getRoleType() == RoleType.ADMIN) {
            return "view/admin.jsp";
        } else {
            return "view/profile.jsp";
        }
    }

    private void setUserAndRoleToSession(HttpServletRequest request, User user, String email) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("email", email);
        session.setAttribute("role", user.getRoleType());
    }

    private User getLoggedUser(String email, String password) {
        return userService.login(email, password);
    }
}