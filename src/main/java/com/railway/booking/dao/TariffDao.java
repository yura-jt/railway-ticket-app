package com.railway.booking.dao;

import com.railway.booking.entity.CarriageType;
import com.railway.booking.entity.Tariff;

import java.util.Optional;

public interface TariffDao extends CrudDao<Tariff> {

    Optional<Tariff> getTariffByCarriageType(CarriageType carriageType);
}
