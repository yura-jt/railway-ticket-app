package com.railway.booking.dao;

import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariConnectionPool implements DatabaseConnector {
    private static final Logger LOGGER = LogManager.getLogger(HikariConnectionPool.class);

    private static final HikariDataSource hikariDataSource = new HikariDataSource();

    public HikariConnectionPool() {
        ResourceBundle resource = getDatabaseConfiguration();

        hikariDataSource.setJdbcUrl(resource.getString("db.url"));
        hikariDataSource.setUsername(resource.getString("db.user"));
        hikariDataSource.setPassword(resource.getString("db.password"));
        hikariDataSource.setMinimumIdle(100);
        hikariDataSource.setMaximumPoolSize(2000);
        hikariDataSource.setAutoCommit(false);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.warn("Failed to return connection", e);
            throw new DatabaseSqlRuntimeException("Can not connect to database");
        }
    }

    private ResourceBundle getDatabaseConfiguration() {
        ResourceBundle appResource = ResourceBundle.getBundle("app.properties");
        String settingFileName = "db/" + appResource.getString("db.mode") + ".properties";
        return ResourceBundle.getBundle(settingFileName);
    }
}