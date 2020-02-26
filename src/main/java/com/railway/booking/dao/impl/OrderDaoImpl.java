package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.OrderDao;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Order;
import com.railway.booking.entity.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderDaoImpl extends AbstractCrudDaoImpl<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);

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
    public Integer saveOrder(Order order) {
        Integer id;
        try (final PreparedStatement preparedStatement
                     = connector.getConnection().prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            insert(preparedStatement, order);
            preparedStatement.execute();
            id = getGeneratedId(preparedStatement);
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", SAVE_QUERY);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
        return id;
    }

    @Override
    protected Order mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Date date = resultSet.getDate("departure_date");
        Timestamp timestamp = new Timestamp(date.getTime());
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        return Order.builder()
                .withId(resultSet.getInt("id"))
                .withDepartureStation(resultSet.getString("departure_station"))
                .withDestinationStation(resultSet.getString("destination_station"))
                .withDepartureDate(dateTime)
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

    private Integer getGeneratedId(PreparedStatement preparedStatement) {
        Integer id = null;
        try (final ResultSet keys = preparedStatement.getGeneratedKeys()) {
            if (keys.next()) {
                id = (int) keys.getLong(1);
            }
        } catch (SQLException e) {
            String message = "Fail to obtain id while saving order";
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
        return id;
    }
}