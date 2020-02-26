package com.railway.booking.command.admin;

import com.railway.booking.command.Command;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.TicketService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminTicketCommand implements Command {
    private final TicketService ticketService;
    private final PageProvider pageProvider;

    public AdminTicketCommand(TicketService ticketService, PageProvider pageProvider) {
        this.ticketService = ticketService;
        this.pageProvider = pageProvider;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);

        List<Ticket> tickets = ticketService.findAll(pageNumber);
        request.setAttribute("tickets", tickets);

        int totalPages = pageProvider.getTotalPages(ticketService.count());

        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", Constants.ITEM_PER_PAGE);

        return "/view/admin/adminTickets.jsp";
    }
}