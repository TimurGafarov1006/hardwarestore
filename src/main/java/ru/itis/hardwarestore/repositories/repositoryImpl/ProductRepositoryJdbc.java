package ru.itis.hardwarestore.repositories.repositoryImpl;

import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.ProductRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.ProductQueries.*;

public class ProductRepositoryJdbc implements ProductRepository {
    private final Properties properties;
    private final String url;

    public ProductRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public void save(Product product) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, product.getName());
            statement.setString(2, product.getSlug());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getCategoryId());
            statement.setDouble(5, product.getPricePerUnit());
            statement.setInt(6, product.getQuantity());
            statement.setString(7, product.getImageUrl());
            statement.setObject(8, product.getCreatedAt(), Types.TIMESTAMP);
            statement.setObject(9, product.getUpdatedAt(), Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setString(1, product.getName());
            statement.setString(2, product.getSlug());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getCategoryId());
            statement.setDouble(5, product.getPricePerUnit());
            statement.setInt(6, product.getQuantity());
            statement.setString(7, product.getImageUrl());
            statement.setObject(8, product.getUpdatedAt(), Types.TIMESTAMP);

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
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                products.add(toProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL))
        {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                products.add(toProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Optional<Product> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(toProduct(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SLUG_SQL))
        {
            statement.setString(1, slug);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(toProduct(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAllLikeName(String name) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_LIKE_NAME_SQL))
        {
            statement.setString(1, name.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                products.add(toProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    private Product toProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("slug"),
                resultSet.getString("description"),
                resultSet.getInt("category_id"),
                resultSet.getDouble("price_per_unit"),
                resultSet.getInt("quantity"),
                resultSet.getString("image_url"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
        return product;
    }
}
