package ru.itis.hardwarestore.exceptions.user;

import ru.itis.hardwarestore.exceptions.UserException;

public class OrderNotFoundException extends UserException {
    public OrderNotFoundException(Integer orderId) {
        super("Order with id %d not found".formatted(orderId));
    }
}
