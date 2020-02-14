package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.PaginationUtil;
import com.railway.booking.service.validator.OrderValidator;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Integer MAX_ORDER_PER_PAGE = 5;
    private final CrudDao<Order> orderDao;
    private final OrderValidator orderValidator;
    private final PaginationUtil paginationUtil;

    public OrderServiceImpl(CrudDao<Order> orderDao, OrderValidator orderValidator,
                            PaginationUtil paginationUtil) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
        this.paginationUtil = paginationUtil;
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
        int maxPage = paginationUtil.getMaxPage(count(), MAX_ORDER_PER_PAGE);
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