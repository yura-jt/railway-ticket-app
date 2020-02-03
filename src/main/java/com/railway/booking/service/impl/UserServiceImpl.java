package com.railway.booking.service.impl;

import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.User;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.UserService;
import com.railway.booking.service.exception.EntityAlreadyExistException;
import com.railway.booking.service.exception.EntityNotFoundException;
import com.railway.booking.service.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final PasswordEncryptor passwordEncryptor;
    private final UserDao userDao;
    private final UserValidator userValidator;
    private static final int USER_PER_PAGE = 5;


    public UserServiceImpl(UserDao userDao, UserValidator userValidator, PasswordEncryptor passwordEncryptor) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public void register(User user) {
        userValidator.validate(user);

        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            String message = String.format("User with such e-mail: %s is absent", user.getEmail());
            LOGGER.warn(message);
            throw new EntityAlreadyExistException(message);
        }
        userDao.save(user);
    }

    @Override
    public User login(String email, String password) {
        validateCredentials(email, password);
        String encryptPassword = passwordEncryptor.encrypt(password);

        User user = userDao.findByEmail(email).orElse(null);

        if (user == null || !user.getPassword().equals(encryptPassword)) {
            String message = String.format("User with email: %s is not registered or password is not correct", email);
            LOGGER.warn(message);
            throw new EntityNotFoundException(message);
        }
        return user;
    }

    @Override
    public List<User> findAll(int pageNumber) {
        int maxPage = getMaxPage();
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        return userDao.findAll(new Page(pageNumber, USER_PER_PAGE));
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

    private int getMaxPage() {
        int totalUsers = (int) userDao.count();
        int page = totalUsers / USER_PER_PAGE;
        if (totalUsers % USER_PER_PAGE != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }

    private void validateCredentials(String email, String password) {
        userValidator.validate(User.builder()
                .withEmail(email)
                .withPassword(password)
                .build());
    }
}