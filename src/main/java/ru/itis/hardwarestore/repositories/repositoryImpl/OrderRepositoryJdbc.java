package ru.itis.hardwarestore.repositories.repositoryImpl;

import ru.itis.hardwarestore.models.Order;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.OrderRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.OrderQueries.*;

public class OrderRepositoryJdbc implements OrderRepository {
    private final Properties properties;
    private final String url;

    public OrderRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public Integer save(Order order) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, order.getUserId());
            statement.setDouble(2, order.getAmountBeforeDiscount());
            statement.setDouble(3, order.getDiscountAmount());
            statement.setDouble(4, order.getTotalAmount());
            statement.setObject(5, order.getCreatedAt(), Types.TIMESTAMP);
            statement.setObject(6, order.getDeliveredAt(), Types.TIMESTAMP);
            statement.setObject(7, order.getClosedAt(), Types.TIMESTAMP);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer createdId = resultSet.getInt(1);
                resultSet.close();
                return createdId;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setDouble(1, order.getAmountBeforeDiscount());
            statement.setDouble(2, order.getDiscountAmount());
            statement.setDouble(3, order.getTotalAmount());
            statement.setObject(4, order.getDeliveredAt(), Types.TIMESTAMP);
            statement.setObject(5, order.getClosedAt(), Types.TIMESTAMP);
            statement.setInt(6, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("user_id"),
                        resultSet.getDouble("amount_before_discount"),
                        resultSet.getDouble("discount_amount"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("delivered_at") != null ? resultSet.getTimestamp("delivered_at").toLocalDateTime() : null,
                        resultSet.getTimestamp("closed_at") != null ? resultSet.getTimestamp("closed_at").toLocalDateTime() : null
                );

                return Optional.ofNullable(order);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Order> findAllByUserId(String userId) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL))
        {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("user_id"),
                        resultSet.getDouble("amount_before_discount"),
                        resultSet.getDouble("discount_amount"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("delivered_at") != null ? resultSet.getTimestamp("delivered_at").toLocalDateTime() : null,
                        resultSet.getTimestamp("closed_at") != null ? resultSet.getTimestamp("closed_at").toLocalDateTime() : null
                );

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}
