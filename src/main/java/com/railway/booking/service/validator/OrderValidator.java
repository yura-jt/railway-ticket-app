package com.railway.booking.service.validator;

import com.railway.booking.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
