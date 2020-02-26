package com.railway.booking.service.impl;

import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.validator.TrainValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
public class TrainServiceImplTest {
    private static final Integer TRAIN_ID = 1;
    private static final Integer PAGE_NUMBER = 1;
    private static final String TRAIN_CODE = "57K";
    private static final String TRAIN_NAME = "Буковинський експрес";

    private static final Train TRAIN =
            new Train(1, "57K", "Буковинський експрес");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private TrainDao trainDao;

    @Mock
    private TrainValidator trainValidator;

    @Mock
    private PageProvider pageProvider;

    @InjectMocks
    private TrainServiceImpl trainService;

    @After
    public void resetMocks() {
        reset(trainDao, trainValidator, pageProvider);
    }

    @Test
    public void count() {
        when(trainDao.count()).thenReturn(1L);

        trainService.count();

        verify(trainDao).count();
    }

    @Test
    public void findByIdShouldReturnSavedTrain() {
        when(trainDao.findById(any())).thenReturn(Optional.of(TRAIN));

        trainService.getById(TRAIN_ID);

        final Train actual = trainService.getById(TRAIN_ID);
        assertEquals(TRAIN, actual);
        verify(trainDao, times(2)).findById(TRAIN_ID);
    }

    @Test
    public void findAllShouldReturnCorrectList() {
        when(trainDao.count()).thenReturn(1L);
        when(trainDao.findAll(any())).thenReturn(Collections.EMPTY_LIST);

        List<Train> actual = trainService.findAll(PAGE_NUMBER);

        assertNotNull(actual);
        verify(trainDao).findAll(any());
    }

    @Test
    public void findAllShouldNotReturnNullIfResultAreAbsent() {
        when(pageProvider.getMaxPage(anyInt(), anyInt())).thenReturn(1);
        when(trainDao.findAll(any(Page.class))).thenReturn(Collections.EMPTY_LIST);

        final List<Train> actual = trainService.findAll(1);
        assertEquals(Collections.EMPTY_LIST, actual);
    }
}