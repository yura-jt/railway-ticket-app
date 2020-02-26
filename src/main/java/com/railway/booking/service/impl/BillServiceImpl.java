package com.railway.booking.service.impl;

import com.railway.booking.dao.BillDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.service.BillService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.util.PageProvider;
import com.railway.booking.service.validator.BillValidator;

import java.util.List;

public class BillServiceImpl implements BillService {
    private final BillDao billDao;
    private final BillValidator billValidator;
    private final PageProvider pageProvider;

    public BillServiceImpl(BillDao billDao, BillValidator billValidator,
                           PageProvider pageProvider) {
        this.billDao = billDao;
        this.billValidator = billValidator;
        this.pageProvider = pageProvider;
    }

    @Override
    public void saveBill(Bill bill) {
        billDao.saveBill(bill);
    }

    @Override
    public void payBill(Bill bill) {
        billValidator.isValid(bill);
        Bill newBill = Bill.builder()
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
    public Integer count() {
        return (int) billDao.count();
    }

    @Override
    public Bill getById(Integer id) {
        billValidator.validateId(id);
        return billDao.findById(id).orElse(null);
    }

    @Override
    public List<Bill> findAll(int pageNumber) {
        int maxPage = pageProvider.getMaxPage(count(), Constants.ITEM_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return billDao.findAll(new Page(pageNumber, Constants.ITEM_PER_PAGE));
    }
}