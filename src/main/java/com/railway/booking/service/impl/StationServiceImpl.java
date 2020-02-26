package com.railway.booking.service.impl;

import com.railway.booking.dao.StationDao;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.Station;
import com.railway.booking.entity.StationType;
import com.railway.booking.service.StationService;

import java.util.Optional;

public class StationServiceImpl implements StationService {
    private final StationDao stationDao;

    public StationServiceImpl(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public Optional<Station> getDepartureStationByOrder(Integer trainId, Order order) {
        Optional<Station> station = stationDao.getStationByTrainAndType(trainId, StationType.DEPARTURE_STATION, order.getDepartureStation());

        return stationDao.getStationByTrainAndType
                (trainId, StationType.DEPARTURE_STATION, order.getDepartureStation());
    }

    @Override
    public Optional<Station> getDestinationStationByOrder(Integer trainId, Order order) {
        Optional<Station> station = stationDao.getStationByTrainAndType
                (trainId, StationType.ARRIVING_STATION, order.getDestinationStation());

        return stationDao.getStationByTrainAndType
                (trainId, StationType.ARRIVING_STATION, order.getDestinationStation());
    }

    @Override
    public int getDistanceBetweenStations(Integer trainId, String departureStation, String destinationStation) {
        int departureDistance = stationDao.getStationDistance
                (trainId, StationType.DEPARTURE_STATION, departureStation);
        int destinationDistance = stationDao.getStationDistance
                (trainId, StationType.ARRIVING_STATION, destinationStation);
        return destinationDistance - departureDistance;
    }
}