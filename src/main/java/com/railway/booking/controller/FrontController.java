package com.railway.booking.controller;

import com.railway.booking.command.Command;
import com.railway.booking.command.user.LoginCommand;
import com.railway.booking.command.user.LogoutCommand;
import com.railway.booking.command.user.RegistrationCommand;
import com.railway.booking.context.ApplicationInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    private final Map<String, Command> commands;

    public FrontController() {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand(ApplicationInjector.getUserService()));
        commands.put("logout", new LogoutCommand(ApplicationInjector.getUserService()));
        commands.put("registration", new RegistrationCommand(ApplicationInjector.getUserService()));
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Command command = getCommand(request);
        String page = command.execute(request, response);

        request.getRequestDispatcher(page).forward(request, response);
    }

    private Command getCommand(HttpServletRequest request) {
        String path = getPageFromPath(request.getRequestURI());

        if (!commands.containsKey(path)) {
            return commands.get("login");
        }
        return commands.get(path);
    }

    private String getPageFromPath(String requestPath) {
        int lastSlashIndex = requestPath.lastIndexOf('/');

        return requestPath.substring(lastSlashIndex + 1);
    }
}