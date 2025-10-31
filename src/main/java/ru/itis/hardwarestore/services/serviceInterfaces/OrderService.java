package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.Order;
import ru.itis.hardwarestore.models.Product;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void createOrder(String userId);
    List<Order> getOrders(String userId);
    Map<Order, Map<Product, Integer>> getOrderContent(Integer orderId);
    Order getOrder(Integer orderId);
}
