package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.Paginator;
import com.railway.booking.service.validator.OrderValidator;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Integer MAX_ORDER_PER_PAGE = 5;
    private final CrudDao<Order> orderDao;
    private final OrderValidator orderValidator;
    private final Paginator paginator;

    public OrderServiceImpl(CrudDao<Order> orderDao, OrderValidator orderValidator,
                            Paginator paginator) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
        this.paginator = paginator;
    }

    @Override
    public Order getById(Integer id) {
        orderValidator.validateId(id);
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public void update(Order order) {
        orderValidator.isValid(order);
    }

    @Override
    public List<Order> findAll(int pageNumber) {
        int maxPage = paginator.getMaxPage(count(), MAX_ORDER_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return orderDao.findAll(new Page(pageNumber, MAX_ORDER_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) orderDao.count();
    }
}