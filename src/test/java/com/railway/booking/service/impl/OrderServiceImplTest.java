package com.railway.booking.service.impl;

import com.railway.booking.dao.OrderDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.OrderStatus;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.validator.OrderValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class OrderServiceImplTest {
    private static final Integer ORDER_ID = 1;
    private static final String DEPARTURE_STATION = "";
    private static final String DESTINATION_STATION = "";
    private static final LocalDateTime DEPARTURE_DATE = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    private static final LocalTime FROM_TIME = LocalTime.now();
    private static final LocalTime TO_TIME = LocalTime.now();
    private static final OrderStatus ORDER_STATUS = OrderStatus.ACCEPTED;
    private static final int PAGE_NUMBER = 3;
    private static final Order ORDER = getOrder();


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private PageProvider pageProvider;

    @InjectMocks
    private OrderServiceImpl orderService;

    @After
    public void resetMocks() {
        reset(orderDao, orderValidator);
    }

    @Test
    public void count() {
        when(orderDao.count()).thenReturn(1L);

        orderService.count();

        verify(orderDao).count();
    }

    @Test
    public void findByIdShouldReturnSavedOrder() {
        orderService.getById(ORDER_ID);
        when(orderDao.findById(ORDER_ID)).thenReturn(Optional.of(ORDER));

        final Order actual = orderService.getById(ORDER_ID);
        assertEquals(ORDER, actual);
        verify(orderDao, times(2)).findById(ORDER_ID);
    }

    @Test
    public void findAllShouldReturnCorrectList() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(PAGE_NUMBER);
        when(orderDao.findAll(any())).thenReturn(Collections.EMPTY_LIST);

        List<Order> actual = orderService.findAll(PAGE_NUMBER);

        assertNotNull(actual);
        verify(orderDao).findAll(any());
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(orderDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<Order> actual = orderService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }

    private static Order getOrder() {
        return Order.builder()
                .withDepartureStation(DEPARTURE_STATION)
                .withDestinationStation(DESTINATION_STATION)
                .withDepartureDate(DEPARTURE_DATE)
                .withToTime(TO_TIME)
                .withFromTime(FROM_TIME)
                .withOrderStatus(ORDER_STATUS)
                .build();
    }
}