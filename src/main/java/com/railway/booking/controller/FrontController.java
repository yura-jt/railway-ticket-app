package com.railway.booking.controller;

import com.railway.booking.command.Command;
import com.railway.booking.command.CommandProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    private final CommandProvider commandProvider;

    public FrontController() {
        commandProvider = new CommandProvider();
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
        Command command = commandProvider.getCommand(request.getRequestURI());
        String page = command.execute(request, response);

        if (page.contains("redirect:")) {
            response.sendRedirect(request.getContextPath() +page.replaceAll("redirect:",""));
            return;
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}