package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Integer save(Order order);
    void update(Order order);
    Optional<Order> findById(int id);
    List<Order> findAllByUserId(UUID userId);
}
