package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.PaginationUtil;
import com.railway.booking.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);

    private static final int TICKET_PER_PAGE = 5;

    private final CrudDao<Ticket> ticketDao;
    private final PaginationUtil paginationUtil;

    public TicketServiceImpl(CrudDao<Ticket> ticketDao, PaginationUtil paginationUtil) {
        this.ticketDao = ticketDao;
        this.paginationUtil = paginationUtil;
    }

    @Override
    public List<Ticket> findAll(int pageNumber) {
        int count = count();
        int maxPage = paginationUtil.getMaxPage(count, TICKET_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return ticketDao.findAll(new Page(pageNumber, TICKET_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) ticketDao.count();
    }
}
