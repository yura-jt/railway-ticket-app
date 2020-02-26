package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.StationDao;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Station;
import com.railway.booking.entity.StationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl extends AbstractCrudDaoImpl<Station> implements StationDao {
    private static final Logger LOGGER = LogManager.getLogger(StationDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM stations WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO stations (name, type, time, distance, train_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM stations LIMIT ? OFFSET ?";
    private static final String FIND_STATION_BY_PARAM_QUERY = "SELECT * FROM stations WHERE train_id = ? AND type = ? and name = ?";
    private static final String FIND_DISTANCE_BY_PARAM_QUERY = "SELECT distance FROM stations WHERE train_id = ? AND type = ? and name = ?";
    private static final String FIND_ALL_STATIONS_BY_TRAIN_QUERY = "SELECT * FROM stations LEFT JOIN trains ON " +
            "stations.train_id = trains.id WHERE trains.id = ?";
    private static final String UPDATE_QUERY = "UPDATE stations SET name = ?, type = ?, time = ?, distance = ?, " +
            "train_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM stations WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM stations";

    public StationDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public List<Station> findAllStationsByTrainId(Integer id) {
        try (final PreparedStatement preparedStatement =
                     connector.getConnection().prepareStatement(FIND_ALL_STATIONS_BY_TRAIN_QUERY)) {
            preparedStatement.setObject(1, id);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Station> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            String message = String.format("Fail to execute findAll query with params, %s", id);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    public Optional<Station> getStationByTrainAndType(Integer trainId, StationType type, String name) {
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(FIND_STATION_BY_PARAM_QUERY)) {
            preparedStatement.setObject(1, trainId);
            preparedStatement.setString(2, type.toString());
            preparedStatement.setObject(3, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (
                SQLException e) {
            String message = String.format("Fail to execute find by param query with param: trainId = %s, " +
                    "stationType = %s, station name = %s", trainId, type, name);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    public Integer getStationDistance(Integer trainId, StationType type, String name) {
        Integer result = null;
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(FIND_DISTANCE_BY_PARAM_QUERY)) {
            preparedStatement.setObject(1, trainId);
            preparedStatement.setString(2, type.toString());
            preparedStatement.setObject(3, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (
                SQLException e) {
            String message = String.format("Fail to execute get station distance with param: trainId = %s, " +
                    "stationType = %s, station name = %s", trainId, type, name);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
        return result;
    }

    @Override
    protected Station mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Station.builder()
                .withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withStationType(StationType.valueOf(resultSet.getString("type").toUpperCase()))
                .withTime(resultSet.getTime("time").toLocalTime())
                .withDistance(resultSet.getInt("distance"))
                .withTrainId(resultSet.getInt("train_id"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Station entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getStationType().toString());
        preparedStatement.setTime(3, Time.valueOf(entity.getTime()));
        preparedStatement.setInt(4, entity.getDistance());
        preparedStatement.setInt(5, entity.getTrainId());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Station entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
    }
}