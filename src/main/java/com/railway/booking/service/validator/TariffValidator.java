package com.railway.booking.service.validator;

import com.railway.booking.entity.Tariff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class TariffValidator implements Validator<Tariff> {
    private static final Logger LOGGER = LogManager.getLogger(TariffValidator.class);

    @Override
    public void validate(Tariff entity) {
        validateId(entity.getId());
        validateRate(entity.getRate());
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided tariff id: %d is not correct," +
                    "id couldn't be null or negative", id);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    private void validateRate(BigDecimal rate) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            String message = ("Provided tariff rate: is not correct," +
                    "rate couldn't be null or negative");
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }
}
