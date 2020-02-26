package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.FlightDao;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Flight;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightDaoImpl extends AbstractCrudDaoImpl<Flight> implements FlightDao {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM flights WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO flights (departure_date, train_id) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM flights LIMIT ? OFFSET ?";
    private static final String FIND_ALL_BY_DATE = "SELECT * FROM flights WHERE departure_date = ?";
    private static final String UPDATE_QUERY = "UPDATE flights SET departure_date = ?, train_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM flights WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM flights";

    public FlightDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public List<Flight> getFlightListByDate(LocalDate localDate) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(FIND_ALL_BY_DATE)) {
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyy-MM-dd"));
            preparedStatement.setObject(1, formattedDate);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Flight> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute getFlightList query with params, %s", localDate);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    protected Flight mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Date date = resultSet.getDate("departure_date");
        Timestamp timestamp = new Timestamp(date.getTime());
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        Integer trainId = resultSet.getInt("train_id");

        return new Flight(id, dateTime, trainId);
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