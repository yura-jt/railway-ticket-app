package com.railway.booking.command.user.booking;

import com.railway.booking.command.Command;
import com.railway.booking.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmTrainCommand implements Command {
    private final OrderService orderService;

    public ConfirmTrainCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/view/confirmTrain.jsp";
    }
}