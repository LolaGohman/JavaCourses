package org.courses.lesson11.repository;

import org.courses.lesson11.dto.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT = "insert into users(username, password, isAdmin) values ( ?, ?, ?)";
    private static final String SQL_UPDATE = "update users set username = ?, password = ?, isAdmin = ? where id = ?";
    private static final String SQL_DELETE = "delete from users where id = ?";
    private static final String SQL_FIND_BY_USERNAME = "select * from users where username = ?";
    private static final String SQL_FIND_BY_ID = "select * from users where id = ?";
    private static final String SQL_FIND_ALL = "select * from users";

    private final ConnectionPool connectionPool;

    public UserRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(User user) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getIsAdmin());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getIsAdmin());
            statement.setLong(4, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> find(String username) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USERNAME)) {
            User user = null;
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                user = getUserFromResultSet(set);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> find(long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            User user = null;
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                user = getUserFromResultSet(set);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                userList.add(getUserFromResultSet(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            return new User(resultSet.getLong("id"), resultSet.getString("username"),
                    resultSet.getString("password"), resultSet.getBoolean("isAdmin"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
