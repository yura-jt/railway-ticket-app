package com.railway.booking.service.impl;

import com.railway.booking.dao.CarriageDao;
import com.railway.booking.entity.Carriage;
import com.railway.booking.service.CarriageService;

import java.util.List;

public class CarriageServiceImpl implements CarriageService {
    private final CarriageDao carriageDao;

    public CarriageServiceImpl(CarriageDao carriageDao) {
        this.carriageDao = carriageDao;
    }

    @Override
    public List<Carriage> findCarriageByFlight(Integer flightId) {
        return carriageDao.findCarriageByFlight(flightId);
    }

    @Override
    public int getAmountReservedInSingleCarriage(Carriage carriage) {
        return carriageDao.getAmountCarriageReservedSeats(carriage.getId());
    }

    @Override
    public int getTotalCarriageCapacity(Integer flightId) {
        List<Carriage> carriages = findCarriageByFlight(flightId);
        return carriages.stream()
                .mapToInt(Carriage::getCapacity)
                .sum();
    }

    @Override
    public int getTotalFreeSeats(Integer flightId) {
        List<Carriage> carriages = findCarriageByFlight(flightId);
        int sum = 0;
        for (Carriage carriage: carriages) {
            int capacity = carriage.getCapacity();
            sum += capacity - getAmountReservedInSingleCarriage(carriage);
        }
        return sum;
    }
}