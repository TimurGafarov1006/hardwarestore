package ru.itis.hardwarestore.repositories.repositoryInterfaces;

import ru.itis.hardwarestore.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    void update(Product product);
    void delete(int id);
    List<Product> findAll();
    List<Product> findByCategoryId(int categoryId);
    Optional<Product> findById(int id);
    Optional<Product> findBySlug(String slug);
    List<Product> findAllLikeName(String name);
}
