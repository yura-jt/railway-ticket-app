package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.util.PageProvider;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {
    private static final Integer TICKET_ID = 1;
    private static final Integer PAGE_NUMBER = 1;

    private static final Ticket TICKET =
            Ticket.builder()
                    .withId(1)
                    .withBillId(2)
                    .withPrice(BigDecimal.valueOf(500))
                    .build();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private CrudDao<Ticket> ticketDao;

    @Mock
    private PageProvider pageProvider;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @After
    public void resetMocks() {
        reset(ticketDao, pageProvider);
    }

    @Test
    public void count() {
        when(ticketDao.count()).thenReturn(1L);

        ticketService.count();

        verify(ticketDao).count();
    }

    @Test
    public void findAllShouldReturnCorrectList() {
        when(ticketDao.count()).thenReturn(1L);
        when(ticketDao.findAll(any())).thenReturn(Collections.EMPTY_LIST);

        List<Ticket> actual = ticketService.findAll(PAGE_NUMBER);

        assertNotNull(actual);
        verify(ticketDao).findAll(any());
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(ticketDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<Ticket> actual = ticketService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }
}