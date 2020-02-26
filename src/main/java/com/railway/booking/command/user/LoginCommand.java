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
            LOGGER.warn(String.format("Failed login attempt: from user with email: %s. " +
                    "User was not found with provided credentials", email));
            return "view/login.jsp";
        }

        setUserAndRoleToSession(request, user);

        if (user.getRoleType() == RoleType.ADMIN) {
            return "view/admin/adminPanel.jsp";
        } else {
            return "view/profile.jsp";
        }
    }

    private void setUserAndRoleToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userFirstName", user.getFirstName());
        session.setAttribute("role", user.getRoleType());
    }

    private User getLoggedUser(String email, String password) {
        return userService.login(email, password);
    }
}