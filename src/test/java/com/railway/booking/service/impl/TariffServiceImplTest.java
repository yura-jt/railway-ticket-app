package com.railway.booking.service.impl;

import com.railway.booking.dao.TariffDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.CarriageType;
import com.railway.booking.entity.Tariff;
import com.railway.booking.service.Paginator;
import com.railway.booking.service.validator.TariffValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TariffServiceImplTest {
    private static final Integer TARIFF_ID = 1;
    private static final CarriageType CARRIAGE_TYPE = CarriageType.COUPE;

    private static final Tariff TARIFF =
            new Tariff(1, CarriageType.LUX, BigDecimal.valueOf(25));

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private TariffDao tariffDao;

    @Mock
    private TariffValidator tariffValidator;

    @Mock
    private Paginator paginator;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @After
    public void resetMocks() {
        reset(tariffDao, tariffValidator, paginator);
    }

    @Test
    public void getRateReturnShouldReturnValidTariff() {
        when(tariffDao.getTariffByCarriageType(any())).thenReturn(Optional.of(TARIFF));
        when(tariffValidator.isValid(TARIFF)).thenReturn(true);
        tariffService.getRate(CARRIAGE_TYPE);

        final BigDecimal actual = tariffService.getRate(CARRIAGE_TYPE);
        assertNotNull(actual);
        verify(tariffDao, times(2)).getTariffByCarriageType(any());
    }

    @Test
    public void findAllShouldReturnCorrectList() {
        when(tariffDao.count()).thenReturn(1L);
        when(tariffDao.findAll(any())).thenReturn(Collections.EMPTY_LIST);

        List<Tariff> actual = tariffService.findAll();

        assertNotNull(actual);
        verify(tariffDao).findAll(any());
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(paginator.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(tariffDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<Tariff> actual = tariffService.findAll();
        assertEquals(Collections.EMPTY_LIST, actual);
    }
}