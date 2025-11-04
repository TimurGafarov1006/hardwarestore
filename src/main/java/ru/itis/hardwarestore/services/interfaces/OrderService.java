package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.Order;
import ru.itis.hardwarestore.models.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    void createOrder(UUID userId);
    List<Order> getOrders(UUID userId);
    Map<Order, Map<Product, Integer>> getOrderContent(Integer orderId);
    Order getOrder(Integer orderId);
}
