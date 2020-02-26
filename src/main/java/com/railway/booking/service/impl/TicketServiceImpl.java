package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);

    private final CrudDao<Ticket> ticketDao;
    private final PageProvider pageProvider;

    public TicketServiceImpl(CrudDao<Ticket> ticketDao, PageProvider pageProvider) {
        this.ticketDao = ticketDao;
        this.pageProvider = pageProvider;
    }

    @Override
    public List<Ticket> findAll(int pageNumber) {
        int count = count();
        int maxPage = pageProvider.getMaxPage(count, Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return ticketDao.findAll(new Page(pageNumber, Constants.ITEM_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) ticketDao.count();
    }
}
