package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.TrainDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainDaoImpl extends AbstractCrudDaoImpl<Train> implements TrainDao {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM trains WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO trains (code, name) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM trains LIMIT ? OFFSET ?";
    private static final String FIND_ALL_TRAINS_BY_FLIGHT_DATE_QUERY = "SELECT * FROM trains LEFT JOIN flights ON " +
            "flights.train_id = trains.id WHERE flights.departure_date = ? LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE trains SET code = ?, name = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM trains WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM trains";

    public TrainDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public List<Train> findAllByFlightDate(LocalDate localDate, Page page) {
        int limit = page.getItemsPerPage();
        int offset = (page.getPageNumber() - 1) * limit;
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(FIND_ALL_TRAINS_BY_FLIGHT_DATE_QUERY)) {
            preparedStatement.setObject(1, localDate);
            preparedStatement.setObject(2, localDate);
            preparedStatement.setObject(3, localDate);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Train> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute findAll query with params, %s", localDate);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    protected Train mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        return new Train(id, code, name);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Train entity) throws SQLException {
        preparedStatement.setString(1, entity.getCode());
        preparedStatement.setString(2, entity.getName());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Train entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }
}