package com.railway.booking.command.admin;

import com.railway.booking.command.Command;
import com.railway.booking.entity.Bill;
import com.railway.booking.service.BillService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminBillCommand implements Command {
    private final BillService billService;
    private final PageProvider pageProvider;

    public AdminBillCommand(BillService billService, PageProvider pageProvider) {
        this.billService = billService;
        this.pageProvider = pageProvider;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);

        List<Bill> bills = billService.findAll(pageNumber);
        request.setAttribute("bills", bills);

        int totalPages = pageProvider.getTotalPages(billService.count());

        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", Constants.ITEM_PER_PAGE);

        return "/view/admin/adminBills.jsp";
    }
}