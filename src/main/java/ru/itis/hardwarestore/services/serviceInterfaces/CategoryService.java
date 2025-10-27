package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(int categoryId);
    List<Category> getChildrenCategories(Integer parentId);
    Category getCategoryBySlug(String slug);
}
