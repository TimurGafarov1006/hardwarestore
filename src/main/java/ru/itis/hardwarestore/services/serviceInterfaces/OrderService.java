package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.Order;

public interface OrderService {
    void createOrder(String userId);
}
