package com.railway.booking.dao.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDaoImpl.class);

    private static final BiConsumer<PreparedStatement, Integer> INT_PARAM_SETTER = (preparedStatement, integer) -> {
        try {
            preparedStatement.setObject(1, integer);
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
    };

    protected static final BiConsumer<PreparedStatement, String> STRING_PARAM_SETTER = (preparedStatement, str) -> {
        try {
            preparedStatement.setObject(1, str);
        } catch (SQLException e) {
            LOGGER.warn(e);

        }
    };

    protected final DatabaseConnector connector;
    private final String findByIdQuery;


    protected AbstractCrudDaoImpl(DatabaseConnector connector, String findByIdQuery) {
        this.connector = connector;
        this.findByIdQuery = findByIdQuery;
    }

    @Override
    public void save(E entity) {

    }

    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, findByIdQuery, INT_PARAM_SETTER);
    }

    protected <P> Optional<E> findByParam(P param, String findByParam, BiConsumer<PreparedStatement, P> designatedParamSetter) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findByParam)) {

            designatedParamSetter.accept(preparedStatement, param);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DatabaseSqlRuntimeException(
                    String.format("Fail to execute find by param query with param: %s", param), e);
        }

        return Optional.empty();
    }

    @Override
    public void update(E entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

}
