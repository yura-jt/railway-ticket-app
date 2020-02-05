package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Flight;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FlightDaoImpl extends AbstractCrudDaoImpl<Flight> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM flights WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO flights (departure_date, train_id) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM flights LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE flights SET departure_date = ?, train_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM flights WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM flights";

    public FlightDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected Flight mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        LocalDateTime departureDate = (resultSet.getDate("departure_date")
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        Integer trainId = resultSet.getInt("train_id");

        return new Flight(id, departureDate, trainId);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Flight entity) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(entity.getDepartureDate().toLocalDate()));
        preparedStatement.setInt(2, entity.getTrainId());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Flight entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }
}