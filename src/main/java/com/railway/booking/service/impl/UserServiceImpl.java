package com.railway.booking.service.impl;

import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.User;
import com.railway.booking.service.PaginationUtil;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.UserService;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final int USER_PER_PAGE = 5;

    private final PasswordEncryptor passwordEncryptor;
    private final UserDao userDao;
    private final UserValidator userValidator;
    private final PaginationUtil paginationUtil;


    public UserServiceImpl(UserDao userDao, UserValidator userValidator, PasswordEncryptor passwordEncryptor,
                           PaginationUtil paginationUtil) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.passwordEncryptor = passwordEncryptor;
        this.paginationUtil = paginationUtil;
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
        int maxPage = paginationUtil.getMaxPage((int) userDao.count(), USER_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        List<User> users = userDao.findAll(new Page(pageNumber, USER_PER_PAGE));

        return users != null ? users : Collections.emptyList();
    }

    @Override
    public User findById(Integer id) {
        userValidator.validateId(id);
        return userDao.findById(id).orElse(null);
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