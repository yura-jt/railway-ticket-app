package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.StationDao;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Station;
import com.railway.booking.entity.enums.StationType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl extends AbstractCrudDaoImpl<Station> implements StationDao {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM stations WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO stations (name, type, time, distance, train_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM stations LIMIT ? OFFSET ?";
    private static final String FIND_ALL_STATIONS_BY_TRAIN_QUERY = "SELECT * FROM trains LEFT JOIN stations ON " +
            "stations.train_id = trains.id WHERE trains.id = ?";
    private static final String UPDATE_QUERY = "UPDATE stations SET name = ?, type = ?, time = ?, distance = ?, " +
            "train_id = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM stations WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM stations";

    public StationDaoImpl(DatabaseConnector connector) {
        super(connector);
    }

    @Override
    public List<Station> findAllStationsByTrainId(Integer id) {
        Page page = new Page(1, Integer.MAX_VALUE);
        return findAll(page, FIND_ALL_STATIONS_BY_TRAIN_QUERY);
    }

    @Override
    public void save(Station entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<Station> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<Station> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(Station entity) {
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