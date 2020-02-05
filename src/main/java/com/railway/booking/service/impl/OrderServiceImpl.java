package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final Integer MAX_ORDER_PER_PAGE = 5;
    private final CrudDao<Order> orderDao;
    private final OrderValidator orderValidator;

    public OrderServiceImpl(CrudDao<Order> orderDao, OrderValidator orderValidator) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
    }

    @Override
    public Order getById(Integer id) {
        orderValidator.validateId(id);
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public void update(Order order) {
        orderValidator.validate(order);

    }

    @Override
    public List<Order> findAll(int pageNumber) {
        int maxPage = getMaxPage();
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return orderDao.findAll(new Page(pageNumber, MAX_ORDER_PER_PAGE));
    }

    private int getMaxPage() {
        int totalUsers = (int) orderDao.count();
        int page = totalUsers / MAX_ORDER_PER_PAGE;
        if (totalUsers % MAX_ORDER_PER_PAGE != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}