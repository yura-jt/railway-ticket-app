package com.railway.booking.command.admin;

import com.railway.booking.command.Command;
import com.railway.booking.command.user.LogoutCommand;
import com.railway.booking.entity.User;
import com.railway.booking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminRemoveUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);
    private static final String ADMIN_USERS_PAGE = "view/admin/adminUsers.jsp";

    private final UserService userService;

    public AdminRemoveUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String userIdParam = request.getParameter("removeId");
        final Integer userId = getIdFromParam(userIdParam);
        System.out.println(userId);
        System.out.println("here");
        if (userId == null || userId <= 0) {
            request.setAttribute("userRemoveError", true);
            return ADMIN_USERS_PAGE;
        }

        User user = userService.findById(userId);
        if (user == null) {
            request.setAttribute("userRemoveError", true);
            return ADMIN_USERS_PAGE;
        }
        boolean isDeleted = userService.deleteUserById(userId);
        if (!isDeleted) {
            request.setAttribute("userRemoveError", true);
            return ADMIN_USERS_PAGE;
        } else {
            request.setAttribute("userSuccessRemoved", true);
        }

        return ADMIN_USERS_PAGE;
    }

    private Integer getIdFromParam(String userIdParam) {
        Integer id = null;
        try {
            id = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            LOGGER.warn(String.format("Can not parse user id for removing, from next string %s", userIdParam));
        }
        return id;
    }
}
