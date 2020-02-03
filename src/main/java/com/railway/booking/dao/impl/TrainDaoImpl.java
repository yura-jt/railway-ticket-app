package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

public class TrainDaoImpl extends AbstractCrudDaoImpl<Train> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM trains WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO trains (code, name) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM trains LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE trains SET code = ?, name = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM trains WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM trains";

    public TrainDaoImpl(DatabaseConnector connector) {
        super(connector);
    }

    @Override
    public void save(Train entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<Train> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<Train> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(Train entity) {
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
    protected Train mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Train.builder()
                .withId(resultSet.getInt("id"))
                .withCode(resultSet.getString("code"))
                .withName(resultSet.getString("name"))
                .withStations(unmodifiableList(new ArrayList<>()))
                .build();
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