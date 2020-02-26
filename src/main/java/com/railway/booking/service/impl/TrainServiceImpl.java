package com.railway.booking.service.impl;

import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.validator.TrainValidator;

import java.time.LocalDate;
import java.util.List;

public class TrainServiceImpl implements TrainService {
    private final TrainValidator trainValidator;
    private final TrainDao trainDao;
    private final PageProvider pageProvider;


    public TrainServiceImpl(TrainDao trainDao, TrainValidator trainValidator, PageProvider pageProvider) {
        this.trainDao = trainDao;
        this.trainValidator = trainValidator;
        this.pageProvider = pageProvider;
    }

    @Override
    public Train getById(Integer id) {
        trainValidator.validateId(id);
        return trainDao.findById(id).orElse(null);
    }

    @Override
    public List<Train> findAll(int pageNumber) {
        int maxPage = pageProvider.getMaxPage(count(), Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return trainDao.findAll(new Page(pageNumber, Constants.ITEM_PER_PAGE));
    }

    @Override
    public List<Train> getTrainScheduleByDate(LocalDate localDate, int pageNumber) {
        trainValidator.validateDate(localDate);
        int maxPage = pageProvider.getMaxPage(count(), Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        return trainDao.findAllByFlightDate(localDate, new Page(pageNumber, Constants.ITEM_PER_PAGE));
    }

    @Override
    public Integer count() {
        return (int) trainDao.count();
    }
}