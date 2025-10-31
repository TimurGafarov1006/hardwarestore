package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    void save(Category category);
    void update(Category category);
    void delete(int id);
    List<Category> findAll();
    List<Category> findByParentId(Integer parentId);
    Optional<Category> findById(int id);
    Optional<Category> findBySlug(String slug);
}
