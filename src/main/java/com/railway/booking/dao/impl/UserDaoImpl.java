package com.railway.booking.dao.impl;

import com.railway.booking.dao.DatabaseConnector;
import com.railway.booking.dao.UserDao;
import com.railway.booking.dao.exception.DatabaseSqlRuntimeException;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String SAVE_QUERY =
            "INSERT INTO users (first_name, last_name, email, phone_number, password, role_type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY =
            "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password= ?, role_type = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM users";

    public UserDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, SAVE_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY);
    }

    @Override
    public boolean deleteUserById(Integer id) throws DatabaseSqlRuntimeException{
        try (final PreparedStatement preparedStatement = connector.getConnection().prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            String message = String.format("Fail to execute delete by id query with id: %d", id);
            LOGGER.warn(message, e);
            throw new DatabaseSqlRuntimeException(message, e);
        }
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .withId(resultSet.getInt("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPhoneNumber(resultSet.getString("phone_number"))
                .withPassword(resultSet.getString("password"))
                .withRoleType(RoleType.valueOf(resultSet.getString("role_type").toUpperCase()))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getPhoneNumber());
        preparedStatement.setString(5, entity.getPassword());
        preparedStatement.setString(6, entity.getRoleType().toString());
    }

    @Override
    protected void update(PreparedStatement preparedStatement, User entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setInt(7, entity.getId());
    }
}