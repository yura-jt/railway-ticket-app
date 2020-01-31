package com.railway.booking.dao.impl;

import com.railway.booking.dao.CrudDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDaoImpl.class);

    private static final BiConsumer<PreparedStatement, Object> OBJ_PARAM_SETTER = (preparedStatement, object) -> {
        try {
            preparedStatement.setObject(1, object);
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
    };

    protected final DatabaseConnector connector;

    protected AbstractCrudDaoImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

    protected void save(E entity, String saveQuery) {
        //todo implement method
    }

    protected List<E> findAll(Page page, String findAllQuery) {
        //todo implement method
        return null;
    }

    protected Optional<E> findById(Integer id, String findByIdQuery) {
        return findByParam(id, findByIdQuery);
    }

    protected <P> Optional<E> findByParam(P param, String findByParam) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(findByParam)) {

            OBJ_PARAM_SETTER.accept(preparedStatement, param);

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


    protected void update(E entity, String updateQuery) {
        //todo implement method
    }

    protected void deleteById(Integer id, String deleteByIdQuery) {
        //todo implement method
    }

    protected Long count(String countQuery) {
        //todo implement method
        return 0L;
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

}