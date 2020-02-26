package com.railway.booking.service;

import com.railway.booking.entity.Order;
import com.railway.booking.entity.Station;

import java.util.Optional;

public interface StationService {
    Optional<Station> getDepartureStationByOrder(Integer trainId, Order order);

    Optional<Station> getDestinationStationByOrder(Integer trainId, Order order);

    int getDistanceBetweenStations(Integer trainId, String departureStation, String destinationStation);
}