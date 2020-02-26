package com.railway.booking.service.impl;

import com.railway.booking.dao.BillDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.validator.BillValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class BillServiceImplTest {
    private static final Integer ORDER_ID = 1;
    private static final BillStatus STATUS = BillStatus.INVOICED;
    private static final BigDecimal PRICE = BigDecimal.valueOf(54.0);
    private static final LocalDateTime CREATED_ON = LocalDateTime.now();
    private static final int PAGE_NUMBER = 3;
    private static final Bill BILL = getBill(BillStatus.INVOICED);
    private static final Bill PAID_BILL = getBill(BillStatus.PAID);
    private static final Bill INVALID_BILL = Bill.builder()
            .withOrderId(null)
            .build();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private BillDao billDao;

    @Mock
    private BillValidator billValidator;

    @Mock
    private PageProvider pageProvider;

    @InjectMocks
    private BillServiceImpl billService;

    @After
    public void resetMocks() {
        reset(billDao, billValidator);
    }

    @Test
    public void payMethodBillShouldSuccessfullyPay() {
        when(billValidator.isValid(any())).thenReturn(true);

        billService.payBill(BILL);

        verify(billValidator, times(2)).isValid(any());
        verify(billDao).update(any());
    }

    @Test
    public void validBillShouldSuccessfullyUpdate() {
        when(billValidator.isValid(BILL)).thenReturn(true);

        billService.update(BILL);

        verify(billValidator, times(1)).isValid(any());
        verify(billDao).update(any());
    }

    @Test
    public void count() {
        when(billDao.count()).thenReturn(1L);

        billService.count();

        verify(billDao).count();
    }

    @Test
    public void findByIdShouldReturnSavedBill() {
        billService.getById(ORDER_ID);
        when(billDao.findById(ORDER_ID)).thenReturn(Optional.of(BILL));

        final Bill actual = billService.getById(ORDER_ID);
        assertEquals(BILL, actual);
        verify(billDao, times(2)).findById(ORDER_ID);
    }

    @Test
    public void findAllShouldReturnCorrectList() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(PAGE_NUMBER);
        when(billDao.findAll(any())).thenReturn(Collections.EMPTY_LIST);

        List<Bill> actual = billService.findAll(PAGE_NUMBER);

        assertNotNull(actual);
        verify(billDao).findAll(any());
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(billDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<Bill> actual = billService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }

    private static Bill getBill(BillStatus billStatus) {
        return Bill.builder().withOrderId(ORDER_ID)
                .withPrice(PRICE)
                .withBillStatus(billStatus)
                .withCreatedOn(CREATED_ON)
                .build();
    }
}