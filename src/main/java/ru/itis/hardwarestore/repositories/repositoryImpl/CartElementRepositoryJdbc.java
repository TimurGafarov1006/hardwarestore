package ru.itis.hardwarestore.repositories.repositoryImpl;

import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.CartElementRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.CartElementsQueries.*;

public class CartElementRepositoryJdbc implements CartElementRepository {
    private final Properties properties;
    private final String url;

    public CartElementRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public void save(CartElement cartElement) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, cartElement.getUserId());
            statement.setInt(2, cartElement.getProductId());
            statement.setInt(3, cartElement.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CartElement cartElement) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setInt(1, cartElement.getQuantity());
            statement.setInt(2, cartElement.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartElement> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CartElement cartElement = new CartElement(
                        resultSet.getInt("id"),
                        resultSet.getString("user_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                );

                return Optional.ofNullable(cartElement);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<CartElement> findAllByUserId(String userId) {
        List<CartElement> cartElements = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL))
        {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartElement cartElement = new CartElement(
                        resultSet.getInt("id"),
                        resultSet.getString("user_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                );
                cartElements.add(cartElement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cartElements;
    }

    @Override
    public Optional<CartElement> findByUserAndProductId(String userId, int productId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_AND_PRODUCT_ID))
        {
            statement.setString(1, userId);
            statement.setInt(2, productId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CartElement cartElement = new CartElement(
                        resultSet.getInt("id"),
                        resultSet.getString("user_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                );

                return Optional.ofNullable(cartElement);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
