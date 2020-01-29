package com.railway.booking.dao;

import java.sql.Connection;

public interface DatabaseConnector {
    Connection getConnection();
}
