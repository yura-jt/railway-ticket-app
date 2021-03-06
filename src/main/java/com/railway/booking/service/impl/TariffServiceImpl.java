package com.railway.booking.service.impl;

import com.railway.booking.dao.TariffDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.CarriageType;
import com.railway.booking.entity.Tariff;
import com.railway.booking.service.TariffService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.validator.TariffValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class TariffServiceImpl implements TariffService {
    private static final Logger LOGGER = LogManager.getLogger(TariffServiceImpl.class);

    private final TariffDao tariffDao;
    private final TariffValidator tariffValidator;

    public TariffServiceImpl(TariffDao tariffDao, TariffValidator tariffValidator) {
        this.tariffDao = tariffDao;
        this.tariffValidator = tariffValidator;
    }

    @Override
    public void update(Tariff tariff) {
        tariffValidator.isValid(tariff);
        tariffDao.update(tariff);
    }

    @Override
    public BigDecimal getRate(CarriageType carriageType) {
        Tariff tariff = tariffDao.getTariffByCarriageType(carriageType).orElse(null);
        return tariff != null ? tariff.getRate() : BigDecimal.ZERO;
    }

    @Override
    public List<Tariff> findAll() {
        return tariffDao.findAll(new Page(1, Constants.ITEM_PER_PAGE));
    }
}