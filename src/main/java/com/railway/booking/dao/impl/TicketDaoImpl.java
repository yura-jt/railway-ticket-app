package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDaoImpl extends AbstractCrudDaoImpl<Ticket> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM tickets WHERE id = ?";
    private static final String SAVE_QUERY =
            "INSERT INTO tickets (departure_station, destination_station, passenger_name, price, flight_id, " +
                    "seat_id, user_id, bill_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM tickets LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY =
            "UPDATE tickets SET departure_station = ?, destination_station = ?, passenger_name = ?, price = ?, " +
                    "flight_id = ?, seat_id = ?, user_id = ?, bill_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM tickets WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM tickets";

    public TicketDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected Ticket mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Ticket.builder()
                .withId(resultSet.getInt("id"))
                .withDepartureStation(resultSet.getString("departure_station"))
                .withDestinationStation(resultSet.getString("destination_station"))
                .withPassengerName(resultSet.getString("passenger_name"))
                .withPrice(resultSet.getBigDecimal("price"))
                .withFlightId(resultSet.getInt("flight_id"))
                .withSeatId(resultSet.getInt("seat_id"))
                .withUserId(resultSet.getInt("user_id"))
                .withBillId(resultSet.getInt("bill_id"))
                .withCreatedOn(resultSet.getTimestamp("created_on").toLocalDateTime())
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Ticket entity) throws SQLException {
        preparedStatement.setString(1, entity.getDepartureStation());
        preparedStatement.setString(2, entity.getDestinationStation());
        preparedStatement.setString(3, entity.getPassengerName());
        preparedStatement.setBigDecimal(4, entity.getPrice());
        preparedStatement.setInt(5, entity.getFlightId());
        preparedStatement.setInt(6, entity.getSeatId());
        preparedStatement.setInt(7, entity.getUserId());
        preparedStatement.setInt(8, entity.getBillId());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Ticket entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(9, entity.getId());
    }
}