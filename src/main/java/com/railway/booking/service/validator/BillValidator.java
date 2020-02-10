package com.railway.booking.service.validator;

import com.railway.booking.entity.Bill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BillValidator implements Validator<Bill> {
    private static final Logger LOGGER = LogManager.getLogger(OrderValidator.class);

    @Override
    public boolean isValid(Bill entity) {
        boolean isValid = false;
        try {
            validateId(entity.getOrderId());
            isValid = true;
        } catch (ValidateException e) {
            LOGGER.warn("Bill entity validation exception occurred");
        }
        return isValid;
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            String message = ("Provided price: is not correct," +
                    "price couldn't be null or negative");
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided Bill id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }
}