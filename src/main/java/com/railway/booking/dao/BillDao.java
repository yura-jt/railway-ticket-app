package com.railway.booking.dao;

import com.railway.booking.entity.Bill;

public interface BillDao extends CrudDao<Bill> {
    void saveBill(Bill bill);

}