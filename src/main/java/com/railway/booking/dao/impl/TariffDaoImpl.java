package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Tariff;
import com.railway.booking.entity.enums.CarriageType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TariffDaoImpl extends AbstractCrudDaoImpl<Tariff> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM tariffs WHERE id = ?";
    private static final String SAVE_QUERY = "INSERT INTO tariffs (carriage_type, rate) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM tariffs LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE tariffs SET carriage_type = ?, rate = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE * FROM tariffs WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM tariffs";

    public TariffDaoImpl(DatabaseConnector connector) {
        super(connector);
    }

    @Override
    public void save(Tariff entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<Tariff> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<Tariff> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(Tariff entity) {
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
    protected Tariff mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        CarriageType carriageType = CarriageType.valueOf(resultSet.getString("carriage_type"));
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
        preparedStatement.setInt(3, entity.getId());
    }
}