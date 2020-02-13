package com.railway.booking.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageUtil {
    private static final Logger LOGGER = LogManager.getLogger(PageUtil.class);

    public int getPageNumberFromString(String page) {
        int result = 1;
        if (page==null) {
            return 1;
        }
        try {
            result = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            LOGGER.warn(String.format("Can not parse page number from string: %s", page), e);
        }
        return result;
    }
}