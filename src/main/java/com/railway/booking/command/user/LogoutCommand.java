package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    private final UserService userService;

    public LogoutCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("role");
        session.invalidate();

        return "view/login.jsp";
    }
}