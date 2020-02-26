package com.railway.booking.command.admin;

import com.railway.booking.command.Command;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.User;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.UserService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminUserCommand implements Command {
    private final UserService userService;
    private final PageProvider pageProvider;

    public AdminUserCommand(UserService userService, PageProvider pageProvider) {
        this.userService = userService;
        this.pageProvider = pageProvider;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);

        List<User> users = userService.findAll(pageNumber);
        request.setAttribute("users", users);

        int totalPages = pageProvider.getTotalPages(userService.count());

        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", Constants.ITEM_PER_PAGE);

        return "view/admin/adminUsers.jsp";
    }
}