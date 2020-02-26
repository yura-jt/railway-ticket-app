package com.railway.booking.service;

import com.railway.booking.entity.Carriage;

import java.util.List;

public interface CarriageService {
    List<Carriage> findCarriageByFlight(Integer flightId);

    int getAmountReservedInSingleCarriage(Carriage carriage);

    int getTotalCarriageCapacity(Integer flightId);

    int getTotalFreeSeats(Integer flightId);
}