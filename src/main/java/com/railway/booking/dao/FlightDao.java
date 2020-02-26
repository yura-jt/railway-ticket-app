package com.railway.booking.dao;

import com.railway.booking.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightDao extends CrudDao<Flight> {
    List<Flight> getFlightListByDate(LocalDate localDate);
}
