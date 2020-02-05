package com.railway.booking.service.impl;

import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.validator.TrainValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class TrainServiceImpl implements TrainService {
    private static final Logger LOGGER = LogManager.getLogger(TrainServiceImpl.class);

    private static final Integer TRAIN_PER_PAGE = 5;
    private final TrainValidator trainValidator;
    private final TrainDao trainDao;

    public TrainServiceImpl(TrainDao trainDao, TrainValidator trainValidator) {
        this.trainDao = trainDao;
        this.trainValidator = trainValidator;
    }

    @Override
    public Train getById(Integer id) {
        trainValidator.validateId(id);
        return trainDao.findById(id).orElse(null);
    }

    @Override
    public List<Train> findAll(int pageNumber) {
        int maxPage = getMaxPage();
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
        int maxPage = getMaxPage();
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        return trainDao.findAllByFlightDate(localDate, new Page(pageNumber, TRAIN_PER_PAGE));
    }

    private int getMaxPage() {
        int totalUsers = (int) trainDao.count();
        int page = totalUsers / TRAIN_PER_PAGE;
        if (totalUsers % TRAIN_PER_PAGE != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}