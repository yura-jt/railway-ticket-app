package com.railway.booking.service;

import com.railway.booking.entity.CarriageType;
import com.railway.booking.entity.Tariff;

import java.math.BigDecimal;
import java.util.List;

public interface TariffService {

    void update(Tariff tariff);

    BigDecimal getRate(CarriageType carriageType);

    List<Tariff> findAll();
}