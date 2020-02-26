package com.railway.booking.dao.impl;

import com.railway.booking.dao.CarriageDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Carriage;
import com.railway.booking.entity.CarriageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarriageDaoImpl extends AbstractCrudDaoImpl<Carriage> implements CarriageDao {
    private static final Logger LOGGER = LogManager.getLogger(CarriageDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM carriages WHERE id = ?";
    private static final String FIND_ALL_BY_FLIGHT_ID_QUERY = "SELECT * FROM carriages WHERE flight_id = ?";
    private static final String SAVE_QUERY = "INSERT INTO carriages (type, number, capacity, flight_id) " +
            "VALUES (?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM carriages LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE carriages SET type = ?, number = ?, capacity = ?," +
            "flight_id = ? where id = ?";
    private static final String COUNT_SEATS_QUERY = "SELECT COUNT(*) FROM seats WHERE carriage_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM carriages WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM carriages";

    public CarriageDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public List<Carriage> findCarriageByFlight(Integer flightId) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(FIND_ALL_BY_FLIGHT_ID_QUERY)) {
            preparedStatement.setObject(1, flightId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Carriage> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute getFlightList query with params, %s", flightId);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    public Integer getAmountCarriageReservedSeats(Integer carriageId) {
        int result = 0;
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(COUNT_SEATS_QUERY)) {
            preparedStatement.setInt(1, carriageId);
            preparedStatement.executeQuery();
            try (final ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    result = (int) resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DatabaseSqlRuntimeException(
                    String.format("Fail to execute query: \"%s\"", COUNT_SEATS_QUERY), e);
        }
        return result;
    }

    @Override
    protected Carriage mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Carriage.builder()
                .withId(resultSet.getInt("id"))
                .withCarriageType(CarriageType.valueOf(resultSet.getString("type")))
                .withNumber(resultSet.getInt("number"))
                .withCapacity(resultSet.getInt("capacity"))
                .withFlightId(resultSet.getInt("flight_id"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Carriage entity) throws SQLException {
        preparedStatement.setInt(1, entity.getId());
        preparedStatement.setString(2, entity.getType().toString());
        preparedStatement.setInt(3, entity.getNumber());
        preparedStatement.setInt(4, entity.getCapacity());
        preparedStatement.setString(5, entity.getFlightId().toString());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Carriage entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
    }
}