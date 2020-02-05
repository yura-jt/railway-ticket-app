package com.railway.booking.service.validator;

import com.railway.booking.entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class TrainValidator implements Validator<Train> {
    private static final Logger LOGGER = LogManager.getLogger(TrainValidator.class);

    private static final Integer MAX_CODE_LENGTH = 10;
    private static final Integer MAX_NAME_LENGTH = 100;

    @Override
    public void validate(Train entity) {
        validateStringLength(entity.getCode(), MAX_CODE_LENGTH);
        validateStringLength(entity.getName(), MAX_NAME_LENGTH);
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided user id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    public void validateDate(LocalDate localDate) {
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            String message = String.format("Local date: %s for train search is expired", localDate.toString());
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    private void validateStringLength(String field, Integer upperBound) {
        if (field == null || field.length() >= upperBound) {
            String message = String.format("Train %s length is out of bound. " +
                    "Maximum allowed length is: %s", field, upperBound);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }
}
