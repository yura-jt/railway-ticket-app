package com.railway.booking.service.impl;

import com.railway.booking.dao.FlightDao;
import com.railway.booking.entity.Flight;
import com.railway.booking.service.FlightService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FlightServiceImpl implements FlightService {
    private final FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public List<Flight> findFlightListByDate(LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();

        return flightDao.getFlightListByDate(date);
    }
}
