package com.railway.booking.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageProvider {
    private static final Logger LOGGER = LogManager.getLogger(PageProvider.class);

    public int getPageNumberFromString(String page) {
        int result = 1;
        if (page == null) {
            return 1;
        }
        try {
            result = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            LOGGER.warn(String.format("Can not parse page number from string: %s", page), e);
        }
        return result;
    }

    public int getTotalPages(int size) {
        int totalPages = size / Constants.ITEM_PER_PAGE;

        if (totalPages % Constants.ITEM_PER_PAGE > 0) {
            totalPages++;
        }
        return totalPages;
    }

    public int getMaxPage(int totalItems, int itemPerPage) {
        int page = totalItems / itemPerPage;
        if (totalItems % itemPerPage != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}