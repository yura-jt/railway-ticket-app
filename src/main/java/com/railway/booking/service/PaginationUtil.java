package com.railway.booking.service;

public class PaginationUtil {

    public int getMaxPage(int totalItems, int itemPerPage) {
        int page = totalItems / itemPerPage;
        if (totalItems % itemPerPage != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}
