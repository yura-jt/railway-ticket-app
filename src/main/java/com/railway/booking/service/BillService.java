package com.railway.booking.service;

import com.railway.booking.entity.Bill;

import java.util.List;

public interface BillService {

    void payBill(Bill bill);

    Bill getById(Integer id);

    List<Bill> findAll(int pageNumber);

    void update(Bill bill);

    Integer count();
}
