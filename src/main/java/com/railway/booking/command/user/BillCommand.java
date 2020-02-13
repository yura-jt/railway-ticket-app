package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.dao.PageUtil;
import com.railway.booking.entity.Bill;
import com.railway.booking.service.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BillCommand implements Command {
    private static final int BILL_PER_PAGE = 5;

    private final BillService billService;
    private final PageUtil pageUtil;

    public BillCommand(BillService billService, PageUtil pageUtil) {
        this.billService = billService;
        this.pageUtil = pageUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageUtil.getPageNumberFromString(page);

        List<Bill> bills = billService.findAll(pageNumber);
        request.setAttribute("bills", bills);

        int totalPages = billService.count() / BILL_PER_PAGE;

        if (totalPages % BILL_PER_PAGE > 0) {
            totalPages++;
        }
        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", BILL_PER_PAGE);

        return "/view/bills.jsp";
    }
}