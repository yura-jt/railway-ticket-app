package com.railway.booking.service.validator;

import com.railway.booking.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    @Override
    public void validate(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("FindByEmail service failed, " +
                    "provided user id: %d couldn't be null or negative", id);
            LOGGER.warn(message);
            throw new ValidateException(message);
        }
    }

    private void validateEmail(String email) {
        if (!checkValidInput(email, EMAIL_REGEX)) {
            throw new ValidateException("E-Mail is not match to requirements");
        }
    }

    private void validatePassword(String password) {
        if (!checkValidInput(password, PASSWORD_REGEX)) {
            throw new ValidateException("Password is not match to requirements");
        }
    }

    private boolean checkValidInput(String input, String regex) {
        return Pattern.matches(regex, input);
    }
}