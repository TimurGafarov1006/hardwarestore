package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.models.Product;

import java.util.List;
import java.util.Map;

public interface CartElementService {
    CartElement getCartElement(Integer id);
    List<CartElement> getUserCart(String userId);
    void addOrUpdate(CartElement cartElement);
    Map<CartElement, Product> getCartContains(String userId);
    void deleteProduct(CartElement cartElement);
    boolean isProductInCart(String userId, int productId);
}
