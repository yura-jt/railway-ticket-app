package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Carriage;
import com.railway.booking.entity.CarriageType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarriageDaoImpl extends AbstractCrudDaoImpl<Carriage> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM carriages WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO carriages (type, number, capacity, flight_id) " +
            "VALUES (?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM carriages LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE carriages SET type = ?, number = ?, capacity = ?," +
            "flight_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM carriages WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM carriages";

    public CarriageDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
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