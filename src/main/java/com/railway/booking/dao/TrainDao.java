package com.railway.booking.dao;

import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainDao extends CrudDao<Train> {

    List<Train> findAllByFlightDate(LocalDate localDate, Page page);
}
