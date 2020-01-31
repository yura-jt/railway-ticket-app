package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.domain.Page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl<E> extends AbstractCrudDaoImpl<E> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String SAVE_QUERY =
            "INSERT INTO users (first_name, last_name, email, phone_number, password, role_type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY =
            "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password= ?, role_type = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE * FROM users WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM users";

    public UserDaoImpl(DatabaseConnector connector) {
        super(connector);
    }

    @Override
    public void save(E entity) {
        save(entity, SAVE_QUERY);
    }

    @Override
    public Optional<E> findById(Integer id) {
        return findById(id, FIND_BY_ID_QUERY);
    }

    @Override
    public List<E> findAll(Page page) {
        return findAll(page, FIND_ALL_QUERY);
    }

    @Override
    public void update(E entity) {
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
    protected E mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }
}