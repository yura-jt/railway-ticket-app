package com.railway.booking.dao;

import com.railway.booking.entity.Carriage;

import java.util.List;

public interface CarriageDao extends CrudDao<Carriage> {
    List<Carriage> findCarriageByFlight(Integer flightId);

    Integer getAmountCarriageReservedSeats(Integer carriageId);
}