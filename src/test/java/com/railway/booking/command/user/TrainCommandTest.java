package com.railway.booking.command.user;

import com.railway.booking.service.util.PageProvider;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainCommandTest {
    private static final int ITEM_PER_PAGE = 5;
    private static final String EXPECTED_VIEW = "/view/trains.jsp";
    private static final int COUNT = 13;
    private static final int PAGE_NUMBER = 2;
    private static final String STRING_PAGE = "2";
    private static final String PARAM = "PAGE";
    private static final List<Train> TRAINS = new ArrayList<>();
    private static final String VIEW_COLLECTION_NAME = "trains";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private TrainService trainService;

    @Mock
    private PageProvider pageProvider;

    @InjectMocks
    private TrainCommand trainCommand;

    @After
    public void resetMocks() {
        reset(request, response, trainService);
    }

    @Test
    public void executeShouldReturnValidView() {
        when(request.getParameter(PARAM)).thenReturn(STRING_PAGE);
        when(pageProvider.getPageNumberFromString(any())).thenReturn(PAGE_NUMBER);
        when(trainService.findAll(PAGE_NUMBER)).thenReturn(TRAINS);
        when(trainService.count()).thenReturn(COUNT);

        String actual = trainCommand.execute(request, response);

        verify(request, times(4)).setAttribute(any(), any());
        assertEquals(EXPECTED_VIEW, actual);
    }

    @Test
    public void executeShouldSetRightAttribute() {
        when(request.getParameter(PARAM)).thenReturn(STRING_PAGE);
        when(pageProvider.getPageNumberFromString(any())).thenReturn(PAGE_NUMBER);
        when(pageProvider.getTotalPages(anyInt())).thenReturn(3);
        when(trainService.findAll(PAGE_NUMBER)).thenReturn(TRAINS);
        when(trainService.count()).thenReturn(COUNT);

        String actual = trainCommand.execute(request, response);

        verify(request).setAttribute("noOfPages", 3);
        verify(request).setAttribute("page", PAGE_NUMBER);
        verify(request).setAttribute("recordsPerPage", ITEM_PER_PAGE);
        assertEquals(EXPECTED_VIEW, actual);
    }

    @Test
    public void executeShouldSetCorrectAttributeForCollectionName() {
        when(request.getParameter(PARAM)).thenReturn(STRING_PAGE);
        when(pageProvider.getPageNumberFromString(any())).thenReturn(PAGE_NUMBER);
        when(trainService.findAll(PAGE_NUMBER)).thenReturn(TRAINS);
        when(trainService.count()).thenReturn(COUNT);

        String actual = trainCommand.execute(request, response);

        verify(request).setAttribute(eq(VIEW_COLLECTION_NAME), any());
        verify(request, times(4)).setAttribute(any(), any());
        assertEquals(EXPECTED_VIEW, actual);
    }
}