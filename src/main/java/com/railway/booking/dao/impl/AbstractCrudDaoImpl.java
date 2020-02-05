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

    private final String FIND_BY_ID_QUERY;
    private final String SAVE_QUERY;
    private final String FIND_ALL_QUERY;
    private final String UPDATE_QUERY;
    private final String DELETE_BY_ID_QUERY;
    private final String COUNT_QUERY;

    private static final BiConsumer<PreparedStatement, Object> OBJ_PARAM_SETTER = (preparedStatement, object) -> {
        try {
            preparedStatement.setObject(1, object);
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
    };

    protected final DatabaseConnector connector;

    protected AbstractCrudDaoImpl(DatabaseConnector connector, String findByIdQuery, String saveQuery, String findAllQuery,
                                  String updateQuery, String deleteByIdQuery, String countQuery) {
        this.connector = connector;
        FIND_BY_ID_QUERY = findByIdQuery;
        SAVE_QUERY = saveQuery;
        FIND_ALL_QUERY = findAllQuery;
        UPDATE_QUERY = updateQuery;
        DELETE_BY_ID_QUERY = deleteByIdQuery;
        COUNT_QUERY = countQuery;
    }

    public List<E> findAll(Page page) {
        int limit = page.getItemsPerPage();
        int offset = (page.getPageNumber() - 1) * limit;
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(FIND_ALL_QUERY)) {
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

    public void save(E entity) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(SAVE_QUERY)) {
            insert(preparedStatement, entity);
            preparedStatement.execute();
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", SAVE_QUERY);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    public void update(E entity) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(UPDATE_QUERY)) {
            update(preparedStatement, entity);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 0) {
                String message = (String.format("Failed to update entity for query: \"%s\"", UPDATE_QUERY));
                LOGGER.warn(message);
                throw new DatabaseSqlRuntimeException(message);
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", UPDATE_QUERY);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    public Optional<E> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    public <P> Optional<E> findByParam(P param, String findByParam) {
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

    public void deleteById(Integer id) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(DELETE_BY_ID_QUERY)) {
            OBJ_PARAM_SETTER.accept(preparedStatement, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            String message = String.format("Fail to execute delete by id query with id: %d", id);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    public long count() {
        long result = 0L;
        try (final Statement statement = connector.getConnection().createStatement()) {
            statement.execute(COUNT_QUERY);
            try (final ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    result = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DatabaseSqlRuntimeException(
                    String.format("Fail to execute query: \"%s\"", COUNT_QUERY), e);
        }
        return result;
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void update(PreparedStatement preparedStatement, E entity) throws SQLException;

}