package com.railway.booking.service;

import com.railway.booking.entity.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {

    Train getById(Integer id);

    List<Train> findAll(int pageNumber);

    List<Train> getTrainScheduleByDate(LocalDate localDate, int pageNumber);

    Integer count();
}
