package ru.itis.hardwarestore.repositories.repositoryInterfaces;

import ru.itis.hardwarestore.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Integer save(Order order);
    void update(Order order);
    Optional<Order> findById(int id);
    List<Order> findAllByUserId(String userId);
}
