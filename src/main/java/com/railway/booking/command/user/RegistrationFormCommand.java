package com.railway.booking.command.user;

import com.railway.booking.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
       return "/view/registration.jsp";
    }
}