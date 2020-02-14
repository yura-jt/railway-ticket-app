package com.railway.booking.service;

import com.railway.booking.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll(int pageNumber);

    Integer count();
}
