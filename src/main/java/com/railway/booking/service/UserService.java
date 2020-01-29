package com.railway.booking.service;

import com.railway.booking.entity.User;

public interface UserService {

    boolean login(String email, String password);

    void register(User user);
}
