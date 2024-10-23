package org.olha_b.repository.impl;

import org.olha_b.exception.DataProcessException;
import org.olha_b.model.User;
import org.olha_b.repository.UserRepository;
import org.olha_b.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    private static final int UPDATE_EMAIL_INDEX = 1;
    private static final int UPDATE_PHONE_INDEX = 2;
    private static final int UPDATE_USER_ID_INDEX = 3;
    private static final int DELETE_USER_ID_INDEX = 1;
    private static final int CREATE_EMAIL_INDEX = 1;
    private static final int CREATE_PHONE_INDEX = 2;
    private static final int GET_USER_ID_INDEX = 1;

    @Override
    public User create(User user) {
        String sqlRequest = "INSERT INTO users (email, phone) VALUES (?, ?)";
        try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(CREATE_EMAIL_INDEX, user.getEmail());
            preparedStatement.setString(CREATE_PHONE_INDEX, user.getPhoneNumber());
            int affectedRows = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (affectedRows > 0 && generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                user.setId(id);
            } else {
                throw new DataProcessException("No rows affected by the create request");
            }
        } catch (SQLException e) {
            throw new DataProcessException("Cannot create a user", e);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        String sqlRequest = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(parseUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessException("Cannot get the list of users", e);
        }
        return users;
    }

    @Override
    public Optional<User> getById(Long id) {
        String sqlRequest = "SELECT * FROM users WHERE id = ?";
        try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setLong(GET_USER_ID_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessException("Cannot get the user", e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        String sqlRequest = "UPDATE users SET email = ?, phone = ? WHERE id = ?";
        try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(UPDATE_EMAIL_INDEX, user.getEmail());
            preparedStatement.setString(UPDATE_PHONE_INDEX, user.getPhoneNumber());
            preparedStatement.setLong(UPDATE_USER_ID_INDEX, user.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessException("No rows affected by the update request");
            }
        } catch (SQLException e) {
            throw new DataProcessException("Cannot update the user", e);
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlRequest = "DELETE FROM users WHERE id = ?";
        try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setLong(DELETE_USER_ID_INDEX, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessException("Cannot delete the user", e);
        }
    }

    private User parseUser(ResultSet requestResult) {
        try {
            Long id = requestResult.getObject(ID, Long.class);
            String email = requestResult.getString(EMAIL);
            String phone = requestResult.getString(PHONE);
            return new User(id, email, phone);
        } catch (SQLException e) {
            throw new DataProcessException("Cannot parse the user", e);
        }
    }
}
