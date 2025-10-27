package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(int productId);
    List<Product> getCategoryProducts(int categoryId);
    Product getProductBySlug(String slug);
}
