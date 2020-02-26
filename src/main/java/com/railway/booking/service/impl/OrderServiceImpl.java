package com.railway.booking.service.impl;

import com.railway.booking.dao.OrderDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.validator.OrderValidator;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderValidator orderValidator;
    private final PageProvider pageProvider;

    public OrderServiceImpl(OrderDao orderDao, OrderValidator orderValidator,
                            PageProvider pageProvider) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
        this.pageProvider = pageProvider;
    }

    @Override
    public Integer saveOrder(Order order) {
        return orderDao.saveOrder(order);
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
        int maxPage = pageProvider.getMaxPage(count(), Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return orderDao.findAll(new Page(pageNumber, Constants.ITEM_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) orderDao.count();
    }
}