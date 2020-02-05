package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.OrderStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.ZoneId;

public class OrderDaoImpl extends AbstractCrudDaoImpl<Order> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM orders WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO orders (departure_station, destination_station, " +
            "departure_date, from_time, to_time, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM orders LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE orders SET departure_station = ?, destination_station = ?, " +
            "departure_date = ?, from_time = ?, to_time = ?, status = ?, user_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM orders WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM orders";

    public OrderDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected Order mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .withId(resultSet.getInt("id"))
                .withDepartureStation(resultSet.getString("departure_station"))
                .withDestinationStation(resultSet.getString("destination_station"))
                .withDepartureDate(resultSet.getDate("departure_date")
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .withFromTime(resultSet.getTime("from_time").toLocalTime())
                .withToTime(resultSet.getTime("to_time").toLocalTime())
                .withOrderStatus(OrderStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .withUserId(resultSet.getInt("user_id"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Order entity) throws SQLException {
        preparedStatement.setString(1, entity.getDepartureStation());
        preparedStatement.setString(2, entity.getDestinationStation());
        preparedStatement.setDate(3, Date.valueOf(entity.getDepartureDate().toLocalDate()));
        preparedStatement.setTime(4, Time.valueOf(entity.getFromTime()));
        preparedStatement.setTime(5, Time.valueOf(entity.getToTime()));
        preparedStatement.setString(6, entity.getStatus().toString());
        preparedStatement.setInt(7, entity.getUserId());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Order entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(8, entity.getId());
    }
}