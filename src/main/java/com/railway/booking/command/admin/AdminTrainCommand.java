package com.railway.booking.command.admin;

import com.railway.booking.command.Command;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminTrainCommand implements Command {
    private final TrainService trainService;
    private final PageProvider pageProvider;

    public AdminTrainCommand(TrainService trainService, PageProvider pageProvider) {
        this.trainService = trainService;
        this.pageProvider = pageProvider;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageProvider.getPageNumberFromString(page);

        List<Train> trains = trainService.findAll(pageNumber);
        request.setAttribute("trains", trains);

        int size = trainService.count();
        int totalPages = pageProvider.getTotalPages(size);

        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", Constants.ITEM_PER_PAGE);

        return "/view/admin/adminTrains.jsp";
    }
}