package com.railway.booking.dao.impl;

import com.railway.booking.dao.BillDao;
import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillDaoImpl extends AbstractCrudDaoImpl<Bill> implements BillDao {
    private static final Logger LOGGER = LogManager.getLogger(BillDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM bills WHERE order_id = ?";
    private static final String SAVE_QUERY = "INSERT INTO bills (order_id, status, price) VALUES (?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM bills LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE seats SET order_id = ?, status = ?, price = ? " +
            "where order_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM bills WHERE order_id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM bills";

    public BillDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public void saveBill(Bill bill) {
        try (final PreparedStatement preparedStatement
                     = connector.getConnection().prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            insert(preparedStatement, bill);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            String message = String.format("Fail to execute next query: %s", SAVE_QUERY);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    protected Bill mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Bill.builder()
                .withOrderId(resultSet.getInt("order_id"))
                .withBillStatus(BillStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .withPrice(resultSet.getBigDecimal("price"))
                .withCreatedOn(resultSet.getTimestamp("created_on").toLocalDateTime())
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Bill entity) throws SQLException {
        preparedStatement.setInt(1, entity.getOrderId());
        preparedStatement.setString(2, entity.getStatus().toString());
        preparedStatement.setBigDecimal(3, entity.getPrice());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Bill entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(4, entity.getOrderId());
    }
}