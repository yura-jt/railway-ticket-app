package com.railway.booking.context;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.HikariConnectionPool;
import com.railway.booking.entity.User;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.Validator;

public class ApplicationInjector {
    private static final DatabaseConnector CONNECTOR = new HikariConnectionPool();

    private static ApplicationInjector applicationInjector;

    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private ApplicationInjector() {
    }

    public static ApplicationInjector getInstance() {
        if (applicationInjector == null) {
            synchronized (ApplicationInjector.class) {
                if (applicationInjector == null) {
                    applicationInjector = new ApplicationInjector();
                }
            }
        }
        return applicationInjector;
    }
}