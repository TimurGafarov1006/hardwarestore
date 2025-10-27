package ru.itis.hardwarestore.services.serviceImpl;

import ru.itis.hardwarestore.exceptions.ProductNotFoundException;
import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.ProductRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product with id %d not found".formatted(productId));
        }
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product getProductBySlug(String slug) {
        Optional<Product> product = productRepository.findBySlug(slug);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product with slug %s not found".formatted(slug));
        }
    }
}
