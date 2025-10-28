package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.CartElement;

import java.util.List;

public interface CartElementService {
    CartElement getCartElement(Integer id);
    List<CartElement> getUserCart(String userId);
    void addOrUpdate(CartElement cartElement);
}
