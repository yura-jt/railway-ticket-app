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
import java.sql.Statement;
import java.util.ArrayList;
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

    protected List<E> findAll(Page page, String findAllQuery) {
        int limit = page.getItemsPerPage();
        int offset = (page.getPageNumber() - 1) * limit;
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(findAllQuery)) {
            preparedStatement.setObject(1, limit);
            preparedStatement.setObject(2, offset);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            String message =
                    String.format("Fail to execute findAll query with params, LIMIT: %d; OFFSET: %d", limit, offset);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    protected void save(E entity, String saveQuery) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(saveQuery)) {
            insert(preparedStatement, entity);
            preparedStatement.execute();
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", saveQuery);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    protected void update(E entity, String updateQuery) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(updateQuery)) {
            update(preparedStatement, entity);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 0) {
                String message = (String.format("Failed to update entity for query: \"%s\"", updateQuery));
                LOGGER.warn(message);
                throw new DatabaseSqlRuntimeException(message);
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", updateQuery);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    protected Optional<E> findById(Integer id, String findByIdQuery) {
        return findByParam(id, findByIdQuery);
    }

    protected <P> Optional<E> findByParam(P param, String findByParam) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(findByParam)) {
            OBJ_PARAM_SETTER.accept(preparedStatement, param);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (
                SQLException e) {
            String message = String.format("Fail to execute find by param query with param: %s", param);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    protected void deleteById(Integer id, String deleteByIdQuery) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(deleteByIdQuery)) {
            OBJ_PARAM_SETTER.accept(preparedStatement, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            String message = String.format("Fail to execute delete by id query with id: %d", id);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    protected Long count(String countQuery) {
        Long result = 0L;
        try (final Statement statement = connector.getConnection().createStatement()) {
            statement.execute(countQuery);
            try (final ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    result = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DatabaseSqlRuntimeException(
                    String.format("Fail to execute query: \"%s\"", countQuery), e);
        }
        return result;
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void update(PreparedStatement preparedStatement, E entity) throws SQLException;

}