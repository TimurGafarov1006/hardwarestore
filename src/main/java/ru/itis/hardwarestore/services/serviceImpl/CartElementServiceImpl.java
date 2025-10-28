package ru.itis.hardwarestore.services.serviceImpl;

import ru.itis.hardwarestore.exceptions.CartException;
import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.CartElementRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.CartElementService;

import java.util.List;
import java.util.Optional;

public class CartElementServiceImpl implements CartElementService {
    private CartElementRepository cartElementRepository;

    public CartElementServiceImpl(CartElementRepository cartElementRepository) {
        this.cartElementRepository = cartElementRepository;
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
            cartElementRepository.save(cartElement);
        }
    }
}
