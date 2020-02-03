package com.railway.booking.service;

import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.User;

import java.util.List;

public interface UserService {

    User login(String email, String password);

    void register(User user);

    List<User> findAll(int pageNumber);

    User findById(Integer id);

    User findByEmail(String email);
}