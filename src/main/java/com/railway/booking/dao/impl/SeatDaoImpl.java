package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;
import com.railway.booking.entity.Seat;
import com.railway.booking.entity.enums.SeatStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SeatDaoImpl extends AbstractCrudDaoImpl<Seat> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM seats WHERE bill_id = ?";
    private static final String SAVE_QUERY = "INSERT INTO seats (bill_id, number, carriage_id, " +
            "ticket_id, status) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM seats LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE seats SET bill_id = ?, number = ?, carriage_id = ?," +
            "ticket_id = ?, status = ? where bill_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM seats WHERE bill_id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM seats";

    public SeatDaoImpl(DatabaseConnector connector) {
        super(connector);
    }

    @Override
    public void save(Seat entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<Seat> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<Seat> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(Seat entity) {
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
    protected Seat mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Seat.builder()
                .withBillId(resultSet.getInt("bill_id"))
                .withNumber(resultSet.getInt("number"))
                .withTicketId(resultSet.getInt("ticket_id"))
                .withCarriageId(resultSet.getInt("ticket_id"))
                .withSeatStatus(SeatStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Seat entity) throws SQLException {
        preparedStatement.setInt(1, entity.getBillId());
        preparedStatement.setInt(2, entity.getNumber());
        preparedStatement.setInt(3, entity.getTicketId());
        preparedStatement.setInt(4, entity.getCarriageId());
        preparedStatement.setString(5, entity.getStatus().toString());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, Seat entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getBillId());
    }
}