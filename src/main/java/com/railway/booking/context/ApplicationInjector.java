package com.railway.booking.context;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.HikariConnectionPool;
import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.impl.UserDaoImpl;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.UserService;
import com.railway.booking.service.impl.UserServiceImpl;
import com.railway.booking.service.validator.UserValidator;

public class ApplicationInjector {
    private static final DatabaseConnector CONNECTOR = new HikariConnectionPool();

    private static ApplicationInjector applicationInjector;

    private static final UserValidator USER_VALIDATOR = new UserValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final DatabaseConnector DATABASE_CONNECTOR = new HikariConnectionPool();

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR, PASSWORD_ENCRYPTOR);

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

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}