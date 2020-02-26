package com.railway.booking.command.user.booking;

import com.railway.booking.command.Command;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.OrderStatus;
import com.railway.booking.service.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MakeOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MakeOrderCommand.class);

    private final OrderValidator orderValidator;

    public MakeOrderCommand(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String departure = request.getParameter("departure");
        final String destination = request.getParameter("destination");
        final String fromTime = request.getParameter("from_time");
        final String toTime = request.getParameter("to_time");
        final String date = request.getParameter("date");

        final boolean isDepartureValid = checkDepartureName(request, departure);
        final boolean isDestinationValid = checkDestinationName(request, destination);
        final boolean isDateValid = checkDate(request, date);
        final boolean isTimeValid = checkTime(request, date, toTime);

        if (!(isDepartureValid && isDestinationValid && isDateValid && isTimeValid)) {
            return "view/orderForm.jsp";
        }
        Integer id = (Integer) request.getSession().getAttribute("userId");

        Order order = Order.builder().withUserId(id)
                .withDepartureStation(departure)
                .withDestinationStation(destination)
                .withDepartureDate(getDate(date).atStartOfDay())
                .withFromTime(getTime(fromTime))
                .withToTime(getTime(toTime))
                .withOrderStatus(OrderStatus.WAITING_FOR_PAYMENT)
                .withUserId(id)
                .build();
        request.getSession().setAttribute("order", order);

        return "redirect:/view/chooseTrain";
    }

    private boolean checkTime(HttpServletRequest request, String date, String time) {
        LocalDate parsedDate = getDate(date);
        boolean isToday = parsedDate != null && parsedDate.equals(LocalDate.now());

        if (!orderValidator.validateInputTime(time, isToday)) {
            request.setAttribute("timeError", "true");
            return false;
        }
        return true;
    }

    private boolean checkDate(HttpServletRequest request, String date) {
        if (!orderValidator.validateInputDate(date)) {
            request.setAttribute("dateError", "true");
            return false;
        }
        return true;
    }

    private boolean checkDepartureName(HttpServletRequest request, String departure) {
        if (!orderValidator.validateInputStationName(departure)) {
            request.setAttribute("departureError", "true");
            return false;
        }
        return true;
    }

    private boolean checkDestinationName(HttpServletRequest request, String destination) {
        if (!orderValidator.validateInputStationName(destination)) {
            request.setAttribute("destinationError", "true");
            return false;
        }
        return true;
    }

    private LocalDate getDate(String date) {
        LocalDate localDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            String message = String.format("Couldn't parse next date: %s", date);
            LOGGER.warn(message, e);
        }
        return localDate;
    }

    private LocalTime getTime(String time) {
        LocalTime localTime = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            localTime = LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            String message = String.format("Couldn't parse next time: %s", time);
            LOGGER.warn(message, e);
        }
        return localTime;
    }
}