package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.models.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartElementService {
    CartElement getCartElement(Integer id);
    List<CartElement> getUserCart(UUID userId);
    void addOrUpdate(CartElement cartElement);
    Map<CartElement, Product> getCartContains(UUID userId);
    void deleteProduct(CartElement cartElement);
    boolean isProductInCart(UUID userId, int productId);
}
