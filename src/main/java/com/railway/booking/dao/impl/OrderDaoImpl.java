package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.enums.OrderStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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
        super(connector);
    }

    @Override
    public void save(Order entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<Order> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(Order entity) {
        update(entity, UPDATE_QUERY);
    }

    @Override
    public void deleteById(Integer id) {
        deleteById(id, DELETE_BY_ID_QUERY);
    }

    @Override
    public long count() {
        return count(COUNT_QUERY);
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