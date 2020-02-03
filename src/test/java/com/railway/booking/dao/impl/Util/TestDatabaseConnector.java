package com.railway.booking.dao.impl.Util;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TestDatabaseConnector implements DatabaseConnector {
    private final HikariDataSource hikariDataSource;

    private static final String H2_PROPERTIES_PATH = "db/test";
    private static final String MYSQL_TEST_URL = "jdbc:mysql://localhost:3306/railway_test";
    private static final String MYSQL_USERNAME = "test";
    private static final String MYSQL_PASSWORD = "test";

    public TestDatabaseConnector(TestDatabase testDatabase) {
        hikariDataSource = new HikariDataSource();

        if (testDatabase == TestDatabase.H2) {
            setH2Config(hikariDataSource);
        } else if (testDatabase == TestDatabase.MYSQL) {
            setMySqlConfig(hikariDataSource);
        }

        hikariDataSource.setMinimumIdle(100);
        hikariDataSource.setMaximumPoolSize(2000);
    }

    private static void setH2Config(HikariDataSource dataSource) {
        ResourceBundle resource = ResourceBundle.getBundle(H2_PROPERTIES_PATH);
        dataSource.setJdbcUrl(resource.getString("db.url"));
        dataSource.setUsername(resource.getString("db.user"));
        dataSource.setPassword(resource.getString("db.password"));
    }

    private static void setMySqlConfig(HikariDataSource dataSource) {
        dataSource.setJdbcUrl(MYSQL_TEST_URL);
        dataSource.setUsername(MYSQL_USERNAME);
        dataSource.setPassword(MYSQL_PASSWORD);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseSqlRuntimeException("Can not obtain connection to the database", e);
        }
    }
}