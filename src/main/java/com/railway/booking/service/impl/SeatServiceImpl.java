package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.entity.Seat;
import com.railway.booking.service.SeatService;

public class SeatServiceImpl implements SeatService {
    private final CrudDao<Seat> seatDao;

    public SeatServiceImpl(CrudDao<Seat> seatDao) {
        this.seatDao = seatDao;
    }

    @Override
    public void save(Seat seat) {
        seatDao.save(seat);
    }
}