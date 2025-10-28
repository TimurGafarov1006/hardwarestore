package ru.itis.hardwarestore.exceptions;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
