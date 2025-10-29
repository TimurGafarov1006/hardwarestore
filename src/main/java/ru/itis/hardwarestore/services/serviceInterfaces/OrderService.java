package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.Order;

import java.util.List;

public interface OrderService {
    void createOrder(String userId);
    List<Order> getOrders(String userId);
}
