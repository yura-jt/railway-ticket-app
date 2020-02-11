package com.railway.booking.dao;

import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class HikariConnectionPool implements DatabaseConnector {
    private static final Logger LOGGER = LogManager.getLogger(HikariConnectionPool.class);

    private static final HikariDataSource hikariDataSource = new HikariDataSource();
    private static final String HIKARI_CONFIG_PROPERTY = "db/hikari";

    public HikariConnectionPool() {
        ResourceBundle resource = getDatabaseConfiguration();
        HikariProfile hikariProfile = new HikariProfile(resource);
        setupHikariPoolConfiguration(hikariProfile);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Failed to return connection", e);
            throw new DatabaseSqlRuntimeException("Can not connect to database");
        }
    }

    private ResourceBundle getDatabaseConfiguration() {
        ResourceBundle appResource = ResourceBundle.getBundle("app");
        String databaseVendorProperty = "db/" + appResource.getString("db.mode");
        return ResourceBundle.getBundle(databaseVendorProperty);
    }

    private void setupHikariPoolConfiguration(HikariProfile hikariProfile) {
        hikariDataSource.setJdbcUrl(hikariProfile.getJdbcUrl());
        hikariDataSource.setUsername(hikariProfile.getUsername());
        hikariDataSource.setPassword(hikariProfile.getPassword());
        hikariDataSource.setDriverClassName(hikariProfile.getDriverClassName());

        hikariDataSource.addDataSourceProperty("cachePrepStmts", hikariProfile.getCachePrepStmts());
        hikariDataSource.addDataSourceProperty("prepStmtCacheSize", hikariProfile.getPrepStmtCacheSize());
        hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", hikariProfile.getPrepStmtCacheSqlLimit());
        hikariDataSource.setMinimumIdle(hikariProfile.getMinimumIdle());
        hikariDataSource.setMaximumPoolSize(hikariProfile.getMaximumPoolSize());
    }

    private static class HikariProfile {
        private final String jdbcUrl;
        private final String username;
        private final String password;
        private final String driverClassName;
        private final String cachePrepStmts;
        private final String prepStmtCacheSize;
        private final String prepStmtCacheSqlLimit;
        private final Integer minimumIdle;
        private final Integer maximumPoolSize;

        private static final Integer DEFAULT_MINIMUM_IDLE = 250;
        private static final Integer DEFAULT_MAXIMUM_POOL_SIZE = 2048;

        public HikariProfile(ResourceBundle resource) {
            jdbcUrl = resource.getString("db.url");
            username = resource.getString("db.user");
            password = resource.getString("db.password");
            driverClassName = resource.getString("db.driver");

            ResourceBundle hikariConfig = ResourceBundle.getBundle(HIKARI_CONFIG_PROPERTY);
            cachePrepStmts = hikariConfig.getString("cache_prep_stmts");
            prepStmtCacheSize = hikariConfig.getString("prep_stmt_cache_size");
            prepStmtCacheSqlLimit = hikariConfig.getString("prep_stmt_cache_sql_limit");
            minimumIdle = getIntParam(hikariConfig, "minimum_idle");
            maximumPoolSize = getIntParam(hikariConfig, "maximum_pool_size");
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public String getCachePrepStmts() {
            return cachePrepStmts;
        }

        public String getPrepStmtCacheSize() {
            return prepStmtCacheSize;
        }

        public String getPrepStmtCacheSqlLimit() {
            return prepStmtCacheSqlLimit;
        }

        public Integer getMinimumIdle() {
            return minimumIdle;
        }

        public Integer getMaximumPoolSize() {
            return maximumPoolSize;
        }

        private Integer getIntParam(ResourceBundle hikariConfig, String key) {
            int result;
            try {
                return Integer.parseInt(hikariConfig.getString(key));
            } catch (NumberFormatException e) {
                LOGGER.warn("Failed to parse hikari config param to integer", e);
            }
            if (key.equals("minimum_idle")) {
                result = DEFAULT_MINIMUM_IDLE;
            } else if (key.equals("maximum_pool_size")) {
                result = DEFAULT_MAXIMUM_POOL_SIZE;
            } else {
                String message = "Couldn't perform Hikari connection pool config";
                LOGGER.error(message);
                throw new DatabaseSqlRuntimeException(message);
            }
            return result;
        }
    }
}