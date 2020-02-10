package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.service.BillService;
import com.railway.booking.service.validator.BillValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BillServiceImpl implements BillService {
    private static final Logger LOGGER = LogManager.getLogger(BillServiceImpl.class);

    private final CrudDao<Bill> billDao;
    private final BillValidator billValidator;

    public BillServiceImpl(CrudDao<Bill> billDao, BillValidator billValidator) {
        this.billDao = billDao;
        this.billValidator = billValidator;
    }

    @Override
    public void payBill(Bill bill) {
        billValidator.isValid(bill);
        Bill newBill = Bill.builder()
                .withUser(bill.getUser())
                .withPrice(bill.getPrice())
                .withBillStatus(BillStatus.PAID)
                .build();
        update(newBill);
    }

    @Override
    public void update(Bill bill) {
        billValidator.isValid(bill);
        billDao.update(bill);
    }

    @Override
    public Bill getById(Integer id) {
        billValidator.validateId(id);
        return billDao.findById(id).orElse(null);
    }

    @Override
    public List<Bill> findAll(int pageNumber) {
        return null;
    }
}
