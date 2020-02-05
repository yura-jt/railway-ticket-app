package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.entity.Tariff;
import com.railway.booking.entity.enums.CarriageType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffDaoImpl extends AbstractCrudDaoImpl<Tariff> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM tariffs WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO tariffs (carriage_type, rate) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM tariffs LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE tariffs SET carriage_type = ?, rate = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM tariffs WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM tariffs";

    public TariffDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected Tariff mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        CarriageType carriageType = CarriageType.valueOf(resultSet.getString("carriage_type").toUpperCase());
        BigDecimal rate = resultSet.getBigDecimal("rate");

        return new Tariff(id, carriageType, rate);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Tariff entity) throws SQLException {
        preparedStatement.setString(1, entity.getCarriageType().toString());
        preparedStatement.setBigDecimal(2, entity.getRate());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Tariff entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }
}