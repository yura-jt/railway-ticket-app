package com.railway.booking.controller;

import com.railway.booking.command.Command;
import com.railway.booking.command.user.LoginCommand;
import com.railway.booking.command.user.LogoutCommand;
import com.railway.booking.command.user.RegistrationCommand;
import com.railway.booking.context.ApplicationInjector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    private final Map<String, Command> commands;

    public UserController() {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand(ApplicationInjector.getUserService()));
        commands.put("logout", new LogoutCommand(ApplicationInjector.getUserService()));
        commands.put("registration", new RegistrationCommand(ApplicationInjector.getUserService()));
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        Command command = getCommand(request);
        command.execute(request, response);
    }


    private Command getCommand(HttpServletRequest request) {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/", "");
        return commands.get(path);
    }
}
