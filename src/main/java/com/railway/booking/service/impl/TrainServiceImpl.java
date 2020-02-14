package com.railway.booking.service.impl;

import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;
import com.railway.booking.service.Paginator;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.validator.TrainValidator;

import java.time.LocalDate;
import java.util.List;

public class TrainServiceImpl implements TrainService {
    private static final int TRAIN_PER_PAGE = 5;

    private final TrainValidator trainValidator;
    private final TrainDao trainDao;
    private final Paginator paginator;


    public TrainServiceImpl(TrainDao trainDao, TrainValidator trainValidator, Paginator paginator) {
        this.trainDao = trainDao;
        this.trainValidator = trainValidator;
        this.paginator = paginator;
    }

    @Override
    public Train getById(Integer id) {
        trainValidator.validateId(id);
        return trainDao.findById(id).orElse(null);
    }

    @Override
    public List<Train> findAll(int pageNumber) {
        int maxPage = paginator.getMaxPage(count(), TRAIN_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return trainDao.findAll(new Page(pageNumber, TRAIN_PER_PAGE));
    }

    @Override
    public List<Train> getTrainScheduleByDate(LocalDate localDate, int pageNumber) {
        trainValidator.validateDate(localDate);
        int maxPage = paginator.getMaxPage(count(), TRAIN_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        return trainDao.findAllByFlightDate(localDate, new Page(pageNumber, TRAIN_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) trainDao.count();
    }
}