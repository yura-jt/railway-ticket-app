package com.railway.booking.dao.impl.Util;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TestDatabaseConnector implements DatabaseConnector {
    private final HikariDataSource hikariDataSource;

    private static final String CONFIG_PROPERTY_NAME = "test_config";
    private static final String PROPERTIES_PATH_PREFIX = "db/";
    private static final String PROPERTY_KEY = "db.vendor";


    public TestDatabaseConnector() {
        hikariDataSource = new HikariDataSource();
        ResourceBundle resourceBundle = getDatabaseConfiguration();
        setDatabaseConfig(resourceBundle);

        hikariDataSource.setMinimumIdle(100);
        hikariDataSource.setMaximumPoolSize(2000);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseSqlRuntimeException("Can not obtain connection to the database", e);
        }
    }

    private void setDatabaseConfig(ResourceBundle resource) {
        hikariDataSource.setJdbcUrl(resource.getString("db.url"));
        hikariDataSource.setUsername(resource.getString("db.user"));
        hikariDataSource.setPassword(resource.getString("db.password"));
    }

    private ResourceBundle getDatabaseConfiguration() {
        ResourceBundle appResource = ResourceBundle.getBundle(CONFIG_PROPERTY_NAME);
        String settingFileName = PROPERTIES_PATH_PREFIX + appResource.getString(PROPERTY_KEY);
        return ResourceBundle.getBundle(settingFileName);
    }
}