package ru.itis.hardwarestore.repositories.impl;

import ru.itis.hardwarestore.models.OrderList;
import ru.itis.hardwarestore.repositories.interfaces.OrderListRepository;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ru.itis.hardwarestore.repositories.queries.OrderListQueries.*;

public class OrderListRepositoryJdbc implements OrderListRepository {
    private final Properties properties;
    private final String url;

    public OrderListRepositoryJdbc() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        properties = PropertiesUtil.getProperties();
        url = properties.getProperty("url");
    }

    @Override
    public void save(OrderList orderList) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL))
        {
            statement.setInt(1, orderList.getOrderId());
            statement.setInt(2, orderList.getProductId());
            statement.setInt(3, orderList.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderList> findAllByOrderId(int orderId) {
        List<OrderList> orderLists = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ORDER_ID_SQL))
        {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderList orderList = new OrderList(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                );

                orderLists.add(orderList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderLists;
    }
}
