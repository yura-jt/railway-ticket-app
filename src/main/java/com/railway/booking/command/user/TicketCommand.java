package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.dao.PageUtil;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TicketCommand implements Command {
    private static final int TICKET_PER_PAGE = 5;

    private final TicketService ticketService;
    private final PageUtil pageUtil;

    public TicketCommand(TicketService ticketService, PageUtil pageUtil) {
        this.ticketService = ticketService;
        this.pageUtil = pageUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageUtil.getPageNumberFromString(page);

        List<Ticket> tickets = ticketService.findAll(pageNumber);
        request.setAttribute("tickets", tickets);

        int totalPages = ticketService.count() / TICKET_PER_PAGE;

        if (totalPages % TICKET_PER_PAGE > 0) {
            totalPages++;
        }
        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", TICKET_PER_PAGE);

        return "/view/tickets.jsp";
    }
}