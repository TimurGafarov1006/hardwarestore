package ru.itis.hardwarestore.services.impl;

import ru.itis.hardwarestore.exceptions.app.CartElementException;
import ru.itis.hardwarestore.exceptions.app.CartException;
import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.repositories.interfaces.CartElementRepository;
import ru.itis.hardwarestore.services.interfaces.CartElementService;
import ru.itis.hardwarestore.services.interfaces.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartElementServiceImpl implements CartElementService {
    private CartElementRepository cartElementRepository;
    private ProductService productService;

    public CartElementServiceImpl(CartElementRepository cartElementRepository, ProductService productService) {
        this.cartElementRepository = cartElementRepository;
        this.productService = productService;
    }

    @Override
    public CartElement getCartElement(Integer id) {
        if (id != null) {
            Optional<CartElement> cartElement = cartElementRepository.findById(id);

            if (cartElement.isPresent()) {
                return cartElement.get();
            } else {
                throw new CartException("Cart element not found");
            }
        }
        throw new CartException("Cart element not found");
    }

    @Override
    public List<CartElement> getUserCart(String userId) {
        return cartElementRepository.findAllByUserId(userId);
    }

    @Override
    public void addOrUpdate(CartElement cartElement) {
        if (cartElement.getId() != null && cartElementRepository.findById(cartElement.getId()).isPresent()) {
            cartElementRepository.update(cartElement);
        } else {
            try {
                cartElementRepository.save(cartElement);
            } catch (CartElementException e) {

            }

        }
    }

    @Override
    public Map<CartElement, Product> getCartContains(String userId) {
        Map<CartElement, Product> cart = new HashMap<>();
        for (CartElement cartElement : getUserCart(userId)) {
            cart.put(cartElement, productService.getProduct(cartElement.getProductId()));
        }
        return cart;
    }

    @Override
    public void deleteProduct(CartElement cartElement) {
        int cartElementId = cartElement.getId();
        cartElementRepository.delete(cartElementId);
    }

    @Override
    public boolean isProductInCart(String userId, int productId) {
        Optional<CartElement> cartElementOptional = cartElementRepository.findByUserAndProductId(userId, productId);

        if (cartElementOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
