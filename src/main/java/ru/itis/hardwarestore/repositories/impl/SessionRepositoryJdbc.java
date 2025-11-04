package ru.itis.hardwarestore.repositories.impl;

import ru.itis.hardwarestore.repositories.interfaces.SessionRepository;
import ru.itis.hardwarestore.models.Session;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

import static ru.itis.hardwarestore.repositories.queries.SessionQueries.*;

public class SessionRepositoryJdbc implements SessionRepository {
    private final String url;
    private final Properties properties;

    public SessionRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }


    @Override
    public void addSession(UUID userId, String sessionId, LocalDateTime expireAt) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, sessionId);
            statement.setObject(2, userId);
            statement.setObject(3, expireAt, Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session getSessionById(String sessionId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setString(1, sessionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Session session = new Session(
                        resultSet.getString("session_id"),
                        resultSet.getObject("user_id", UUID.class),
                        resultSet.getTimestamp("expire_at").toLocalDateTime()
                );
                return session;
            }
            throw new IllegalArgumentException("Session not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSessionById(String sessionId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL))
        {
            statement.setString(1, sessionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
