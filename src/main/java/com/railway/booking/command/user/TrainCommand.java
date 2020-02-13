package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.dao.PageUtil;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TrainCommand implements Command {
    private static final int TRAIN_PER_PAGE = 5;

    private final TrainService trainService;
    private final PageUtil pageUtil;

    public TrainCommand(TrainService trainService, PageUtil pageUtil) {
        this.trainService = trainService;
        this.pageUtil = pageUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageNumber = pageUtil.getPageNumberFromString(page);

        List < Train > trains = trainService.findAll(pageNumber);
        request.setAttribute("trains", trains);

        int size = trainService.count();
        int totalPages = size / TRAIN_PER_PAGE;

        if (totalPages % TRAIN_PER_PAGE > 0) {
            totalPages++;
        }
        request.setAttribute("noOfPages", totalPages);
        request.setAttribute("page", pageNumber);
        request.setAttribute("recordsPerPage", TRAIN_PER_PAGE);

        return "/view/trains.jsp";
    }
}