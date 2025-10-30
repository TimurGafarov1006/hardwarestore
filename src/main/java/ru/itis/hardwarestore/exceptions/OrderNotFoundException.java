package ru.itis.hardwarestore.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer orderId) {
        super("Order with id %d not found".formatted(orderId));
    }
}
