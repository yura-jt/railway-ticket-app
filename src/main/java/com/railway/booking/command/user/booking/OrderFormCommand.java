package com.railway.booking.command.user.booking;

import com.railway.booking.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/view/orderForm.jsp";
    }
}