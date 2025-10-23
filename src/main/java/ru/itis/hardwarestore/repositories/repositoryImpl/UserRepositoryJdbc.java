package ru.itis.hardwarestore.repositories.repositoryImpl;

import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.models.UserRole;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.UserQueries.*;


public class UserRepositoryJdbc implements UserRepository {
    private final Properties properties;
    private final String url;

    public UserRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("url");
    }


    @Override
    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, user.getId());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getPasswordHash());
            statement.setString(6, user.getSalt());
            statement.setString(7, user.getPhone());
            statement.setString(8, user.getEmail());
            statement.setObject(9, user.getBirthday(), Types.DATE);
            statement.setObject(10, user.getCreatedAt(), Types.TIMESTAMP);
            statement.setObject(11, user.getUpdatedAt(),Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User editedUser) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setString(1, editedUser.getFirstName());
            statement.setString(2, editedUser.getLastName());
            statement.setString(3, editedUser.getPasswordHash());
            statement.setString(4, editedUser.getSalt());
            statement.setString(5, editedUser.getPhone());
            statement.setString(6, editedUser.getEmail());
            statement.setObject(7, editedUser.getUpdatedAt(), Types.TIMESTAMP);
            statement.setString(8, editedUser.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL))
        {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("id"),
                        UserRole.valueOf(resultSet.getString("role")),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("salt"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getDate("birthday").toLocalDate(),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("id"),
                        UserRole.valueOf(resultSet.getString("role")),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("salt"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getDate("birthday").toLocalDate(),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                return Optional.ofNullable(user);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL))
        {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("id"),
                        UserRole.valueOf(resultSet.getString("role")),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("salt"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getDate("birthday").toLocalDate(),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                return Optional.ofNullable(user);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
