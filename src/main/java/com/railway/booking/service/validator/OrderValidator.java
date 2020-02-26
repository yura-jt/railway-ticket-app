package com.railway.booking.service.validator;

import com.railway.booking.entity.Order;
import com.railway.booking.service.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderValidator implements Validator<Order> {
    private static final Logger LOGGER = LogManager.getLogger(OrderValidator.class);

    @Override
    public boolean isValid(Order entity) {
        boolean isValid = false;
        try {
            validateId(entity.getId());
            isValid = true;
        } catch (ValidateException e) {
            LOGGER.warn("Order entity validation exception occurred");
        }
        return isValid;
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided order id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    public boolean validateInputStationName(String stationName) {
        return !(stationName == null || stationName.isEmpty() || stationName.length() < 2);
    }

    public boolean validateInputDate(String date) {
        LocalDate localDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.warn(String.format("Couldn't parse next date: %s", date), e);
        }
        return localDate != null && localDate.isAfter(LocalDate.now().minusDays(1));
    }

    public boolean validateInputTime(String time, boolean isToday) {
        LocalTime localTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            localTime = LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            String message = String.format("Couldn't parse next time: %s", time);
            LOGGER.warn(message, e);
            throw new ValidateException(message);
        }
        boolean isAfter = true;
        if (isToday) {
            isAfter = localTime.isAfter
                    (LocalTime.now().plusMinutes(Constants.MIN_TIME_BETWEEN_ORDER_AND_DEPARTURE_TIME));
        }
        return localTime != null && isAfter;
    }
}
