package com.railway.booking.service;

import com.railway.booking.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    List<Flight> findFlightListByDate(LocalDateTime localDateTime);

}