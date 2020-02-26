package com.railway.booking.service.impl;

import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.User;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.util.PasswordEncryptor;
import com.railway.booking.service.UserService;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final PasswordEncryptor passwordEncryptor;
    private final UserDao userDao;
    private final UserValidator userValidator;
    private final PageProvider pageProvider;


    public UserServiceImpl(UserDao userDao, UserValidator userValidator, PasswordEncryptor passwordEncryptor,
                           PageProvider pageProvider) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.passwordEncryptor = passwordEncryptor;
        this.pageProvider = pageProvider;
    }

    @Override
    public boolean register(User user) {
        if (!userValidator.isValid(user)) {
            return false;
        }

        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            LOGGER.warn(String.format("User with such e-mail: %s is already exist", user.getEmail()));
            return false;
        }
        String encodedPassword = passwordEncryptor.encrypt(user.getPassword());
        User userToPersist = User.builder(user).withPassword(encodedPassword).build();

        return userDao.save(userToPersist);
    }

    @Override
    public User login(String email, String password) {
        User user = null;
        if (!isValidCredentials(email, password)) {
            return user;
        }

        String encryptPassword = passwordEncryptor.encrypt(password);
        user = userDao.findByEmail(email).orElse(null);

        if (user == null || !user.getPassword().equals(encryptPassword)) {
            LOGGER.warn(String.format("User with email: %s is not registered or password is not correct", email));
            user = null;
        }
        return user;
    }

    @Override
    public List<User> findAll(int pageNumber) {
        int maxPage = pageProvider.getMaxPage((int) userDao.count(), Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        List<User> users = userDao.findAll(new Page(pageNumber, Constants.ITEM_PER_PAGE));

        return users != null ? users : Collections.emptyList();
    }

    @Override
    public User findById(Integer id) {
        userValidator.validateId(id);
        return userDao.findById(id).orElse(null);
    }

    @Override
    public Integer count() {
        return (int) userDao.count();
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        userValidator.validateId(userId);
        try {
            userDao.deleteUserById(userId);
            return true;
        } catch (DatabaseSqlRuntimeException e) {
            LOGGER.error(String.format("Couldn't delete user with id = %s", userId), e);
        }
        return false;
    }

    private boolean isValidCredentials(String email, String password) {
        boolean isValid = false;
        try {
            userValidator.isValid(User.builder()
                    .withEmail(email)
                    .withPassword(password)
                    .build());
            isValid = true;
        } catch (ValidateException e) {
            LOGGER.warn(String.format("Credentials, provided for email: %s are not valid ", email));
        }
        return isValid;
    }
}