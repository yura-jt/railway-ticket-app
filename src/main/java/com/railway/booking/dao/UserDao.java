package com.railway.booking.dao;

import com.railway.booking.entity.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    Optional<User> findByEmail(String email);

    boolean deleteUserById(Integer userId);
}
