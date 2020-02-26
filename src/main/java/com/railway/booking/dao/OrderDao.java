package com.railway.booking.dao;

import com.railway.booking.entity.Order;

public interface OrderDao extends CrudDao<Order> {
    Integer saveOrder(Order order);

}