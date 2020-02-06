package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private final UserService userService;

    public LogoutCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("username");
        session.invalidate();

        return "view/login.jsp";
    }
}