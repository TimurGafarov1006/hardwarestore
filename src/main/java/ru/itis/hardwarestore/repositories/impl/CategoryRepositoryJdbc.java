package ru.itis.hardwarestore.repositories.impl;

import ru.itis.hardwarestore.models.Category;
import ru.itis.hardwarestore.repositories.interfaces.CategoryRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.CategoryQueries.*;

public class CategoryRepositoryJdbc implements CategoryRepository {
    private final Properties properties;
    private final String url;

    public CategoryRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public void save(Category category) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.setString(3, category.getSlug());
            statement.setString(4, category.getImageUrl());
            statement.setObject(5, category.getCreatedAt(), Types.TIMESTAMP);
            statement.setObject(6, category.getUpdatedAt(), Types.TIMESTAMP);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL))
        {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.setString(3, category.getSlug());
            statement.setString(4, category.getImageUrl());
            statement.setObject(5, category.getCreatedAt(), Types.TIMESTAMP);

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
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("parent_id"),
                        resultSet.getString("slug"),
                        resultSet.getString("image_url"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at") != null
                                ? resultSet.getTimestamp("updated_at").toLocalDateTime()
                                : null
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public List<Category> findByParentId(Integer parentId) {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties))
        {
            PreparedStatement statement;
            if (parentId == null) {
                statement = connection.prepareStatement(FIND_BY_PARENT_ID_IS_NULL_SQL);
            } else {
                statement = connection.prepareStatement(FIND_BY_PARENT_ID_SQL);
                statement.setInt(1, parentId);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parentId != null ? resultSet.getInt("parent_id") : null,
                        resultSet.getString("slug"),
                        resultSet.getString("image_url"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at") != null
                                ? resultSet.getTimestamp("updated_at").toLocalDateTime()
                                : null
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public Optional<Category> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("parent_id") != 0 ? resultSet.getInt("parent_id") : null,
                        resultSet.getString("slug"),
                        resultSet.getString("image_url"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at") != null
                                ? resultSet.getTimestamp("updated_at").toLocalDateTime()
                                : null
                );

                return Optional.ofNullable(category);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SLUG_SQL))
        {
            statement.setString(1, slug);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("parent_id") != 0 ? resultSet.getInt("parent_id") : null,
                        resultSet.getString("slug"),
                        resultSet.getString("image_url"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at") != null
                                ? resultSet.getTimestamp("updated_at").toLocalDateTime()
                                : null
                );

                return Optional.ofNullable(category);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
