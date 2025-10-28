package ru.itis.hardwarestore.repositories.repositoryInterfaces;

import ru.itis.hardwarestore.models.CartElement;

import java.util.List;
import java.util.Optional;

public interface CartElementRepository {
    void save(CartElement cartElement);
    void update(CartElement cartElement);
    void delete(int id);
    Optional<CartElement> findById(int id);
    List<CartElement> findAllByUserId(String userId);
}
