package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.dao.PageProvider;
import com.railway.booking.entity.Order;
import com.railway.booking.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderCommand implements Command {
    private static final int ORDER_PER_PAGE = 5;

    private final OrderService orderService;
    private final PageProvider pageProvider;

    public OrderCommand(OrderService orderService, PageProvider pageProvider) {
        this.orderService = orderService;
        this.pageProvider = pageProvider;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);

        List<Order> orders = orderService.findAll(pageNumber);
        request.setAttribute("orders", orders);

        int totalPages = orderService.count() / ORDER_PER_PAGE;

        if (totalPages % ORDER_PER_PAGE > 0) {
            totalPages++;
        }
        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", ORDER_PER_PAGE);

        return "/view/orders.jsp";
    }
}