package com.railway.booking.command.user;

import com.railway.booking.command.Command;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("password_repeated");

        if (!password.equals(passwordRepeat)) {
            return "registrationForm";
        }

        User user = User.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .withPassword(password)
                .withRoleType(RoleType.PASSENGER)
                .build();

        if (userService.register(user)) {
            return "/view/profile.jsp";
        }
        return "/view/registration.jsp";
    }
}