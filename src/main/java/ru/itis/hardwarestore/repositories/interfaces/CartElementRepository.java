package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.CartElement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartElementRepository {
    void save(CartElement cartElement);
    void update(CartElement cartElement);
    void delete(int id);
    Optional<CartElement> findById(int id);
    List<CartElement> findAllByUserId(UUID userId);
    Optional<CartElement> findByUserAndProductId(UUID userId, int productId);
}
