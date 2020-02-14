package com.railway.booking.service.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.service.BillService;
import com.railway.booking.service.Paginator;
import com.railway.booking.service.validator.BillValidator;

import java.util.List;

public class BillServiceImpl implements BillService {
    private static final Integer MAX_BILL_PER_PAGE = 5;

    private final CrudDao<Bill> billDao;
    private final BillValidator billValidator;
    private final Paginator paginator;

    public BillServiceImpl(CrudDao<Bill> billDao, BillValidator billValidator,
                           Paginator paginator) {
        this.billDao = billDao;
        this.billValidator = billValidator;
        this.paginator = paginator;
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
        int maxPage = paginator.getMaxPage(count(), MAX_BILL_PER_PAGE);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        return billDao.findAll(new Page(pageNumber, MAX_BILL_PER_PAGE));
    }
}
