package com.railway.booking.service.validator;

import com.railway.booking.entity.User;

import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&*+/=?`{}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    @Override
    public void validate(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
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