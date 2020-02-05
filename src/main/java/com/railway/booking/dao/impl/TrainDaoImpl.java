package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainDaoImpl extends AbstractCrudDaoImpl<Train> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM trains WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO trains (code, name) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM trains LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE trains SET code = ?, name = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM trains WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM trains";

    public TrainDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
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