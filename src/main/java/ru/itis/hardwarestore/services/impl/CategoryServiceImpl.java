package ru.itis.hardwarestore.services.impl;

import ru.itis.hardwarestore.exceptions.user.CategoryNotFoundException;
import ru.itis.hardwarestore.models.Category;
import ru.itis.hardwarestore.repositories.interfaces.CategoryRepository;
import ru.itis.hardwarestore.services.interfaces.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new CategoryNotFoundException("Category with id %d not found".formatted(categoryId));
        }
    }

    @Override
    public List<Category> getChildrenCategories(Integer parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        Optional<Category> category = categoryRepository.findBySlug(slug);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new CategoryNotFoundException("Category with slug %s not found".formatted(slug));
        }
    }
}
