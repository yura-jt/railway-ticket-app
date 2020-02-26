package com.railway.booking.service;

import com.railway.booking.entity.Order;

import java.util.List;

public interface OrderService {
    Integer saveOrder(Order order);

    Order getById(Integer id);

    void update(Order order);

    List<Order> findAll(int pageNumber);

    Integer count();
}
